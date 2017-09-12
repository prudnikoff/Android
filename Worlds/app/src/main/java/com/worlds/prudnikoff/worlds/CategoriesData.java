package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

class CategoriesData implements Serializable {

    private static final String nameToSave = "categories";
    private static ArrayList<CategoryModel> categories = new ArrayList<>();

    static int getNumOfCategories() {
        return categories.size();
    }

    static ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    private static void setCategories(ArrayList<CategoryModel> mCategories) {
        categories = mCategories;
    }

    static CategoryModel getCategory(int position) {
        return categories.get(position);
    }

    static int getCategoryPosition(String name) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getNameOfCategory().equals(name)) {
                return i;
            }
        }
        return 0;
    }

    static void deleteCategory(int position) {
        categories.remove(position);
    }

    static void createNewCategory(Context context, String nameOfCategory) {
        String name = nameOfCategory.replaceAll(" ", "");
        if (name.length() > 0) {
            String currentDateAndTime = java.text.DateFormat.getDateTimeInstance()
                    .format(Calendar.getInstance().getTime());
            CategoryModel newCategory = new CategoryModel(nameOfCategory, currentDateAndTime);
            categories.add(0, newCategory);
            CategoriesData.toLow(0);
            MainActivity.notifyAboutCategoriesChanging();
        } else Toast.makeText(context, "Sorry, the field cant't be empty", Toast.LENGTH_LONG).show();
    }

    static String[] getStringListOfCategories() {
        String[] listOfCategories = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            listOfCategories[i] = categories.get(i).getNameOfCategory();
        }
        return listOfCategories;
    }

    static void toTop(int categoryPosition) {
        CategoryModel category = categories.get(categoryPosition);
        categories.remove(categoryPosition);
        categories.add(0, category);
    }

    static void toLow(int categoryPosition) {
        CategoryModel category = categories.get(categoryPosition);
        categories.remove(categoryPosition);
        int i = 0;
        while (i < categories.size()) {
            if (!categories.get(i).isTop()) break;
            i++;
        }
        categories.add(i, category);
    }

    static void saveCurrentState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context
                .getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        int numOfLaunches = sharedPreferences.getInt("numOfLaunches", 0);
        numOfLaunches++;
        editor.putInt("numOfLaunches", numOfLaunches);
        editor.putString(nameToSave, gson.toJson(categories));
        editor.apply();
    }

    static void restoreState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context
                .getApplicationInfo().name, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(nameToSave, "");
        if (json.length() > 0) {
            Type type = new TypeToken<ArrayList<CategoryModel>>() {
            }.getType();
            ArrayList<CategoryModel> categories = gson.fromJson(json, type);
            CategoriesData.setCategories(categories);
        }
    }

    static void backUpData(Context context, String fileName) {
        FileOutputStream fos = null;
        Gson gson = new Gson();
        try {
            File root = new File(Environment.getExternalStorageDirectory().getPath(), "Worlds");
            if (!root.exists()) {
                root.mkdirs();
            }
            File backUp = new File(root, fileName + ".txt");
            fos = new FileOutputStream(backUp);
            byte[] buffer = gson.toJson(categories).getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
            Toast.makeText(context, "Done! Check it in /sdcard/Worlds", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Log.e("Back up", "File isn't exist");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("Back up", "Something wrong with IO");
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e("Back up", "Something wrong with IO");
            }
        }
    }

    static void restoreData(Context context, String fileName) {
        FileInputStream fis = null;
        Gson gson = new Gson();
        try {
            File backUp = new File(Environment.getExternalStorageDirectory().getPath(), "Worlds/" + fileName + ".txt");
            fis = new FileInputStream(backUp);
            int length = (int) backUp.length();
            byte[] buffer = new byte[length];
            fis.read(buffer, 0, length);
            String json = new String(buffer, "UTF-8");
            if (json.length() > 0) {
                Type type = new TypeToken<ArrayList<CategoryModel>>() {
                }.getType();
                ArrayList<CategoryModel> categories = gson.fromJson(json, type);
                CategoriesData.getCategories().clear();
                CategoriesData.getCategories().addAll(categories);
            }
            fis.close();
            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
            MainActivity.notifyAboutCategoriesChanging();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File wasn't found", Toast.LENGTH_LONG).show();
            Log.e("Restore", "File isn't exist");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Restore", "Something wrong with IO");
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e("Restore", "Something wrong with IO");
            }
        }
    }

    static void startParse(Context context, Database db) {
        String start = "OPTED v0.03 Letter ";
        String end = ".html";
        for (int i = 0; i < 26; i++) {
            String name = start + (char) (65 + i) + end;
            parse(context, name, db);
        }
    }

    static void parse(Context context, String path, Database db) {
        try {
            String pah = context.getAssets() + path;
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(path)));
            int i = 0;
            do {
                String headword = reader.readLine();
                if (headword.equals("</body></html>")) break;
                String pos = reader.readLine();
                String def = reader.readLine();
                db.addWord(headword, def, pos);
                i++;
            } while (true);
            System.out.println(i);
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void createFirstLaunchCategories(Context context) {
        createNewCategory(context, "From books");
        createNewCategory(context, "People");
        createNewCategory(context, "Work");
        CategoryModel category = categories.get(0);
        category.changeTop();
        category.addWord(new WordModel("verb", "employ", "to pay someone to work for you",
                "The factory employs over 2,000 people."));
        category.addWord(new WordModel("noun", "processing", "when a substance is changed as " +
                "part of th manufacture of a product", "Fire broke out at a food processing " +
                "plant in Hamlet, N. C."));
        category.addWord(new WordModel("noun", "flash memory", "a type of computer memory that " +
                "can continue storing information without a power supply. It is used, " +
                "for example, in memory cards", null));
        category.getWord(1).setMemorized(true);
        category = categories.get(2);
        category.addWord(new WordModel("verb", "domesticate", "to make an animal able to work " +
                "for people or live with them as a pet", "She domesticated a cat."));
        category.addWord(new WordModel("noun", "sail", "a large piece of strong cloth fixed " +
                "onto a boat, so that the wind will push the boat along", "a yacht with white sails"));
        category.getWord(0).setMemorized(true);
        category = categories.get(1);
        category.addWord(new WordModel(null, "Putin, Vladimir", "(1952–) a Russian politician " +
                "who was President of Russia (2000–08). He has twice been Prime Minister of " +
                "Russia (1999-2000 and 2008-). When he was president, he was known as a strong " +
                "leader who supported the Russian military in Chechnya. Before becoming " +
                "president, Putin worked for the KGB for many years.", null));
        category.addWord(new WordModel(null, "London, Jack", "(1876–1916) a US writer of " +
                "adventure novels, best known forThe Call of the Wild andWhite Fang", null));
        category.addWord(new WordModel(null, "Chekhov, Anton", "(1860–1904) a Russian writer " +
                "of plays and short stories, best known for his playsThe Seagull,Uncle Vanya, " +
                "and The Cherry Orchard", null));
        category.addWord(new WordModel(null, "Jobs, Steve", "(1955–) a US computer designer and " +
                "businessman who, together with Steve Wozniak, designed and built the first real " +
                "personal computer and started the Apple computer company. In 1985 he left Apple" +
                " and bought the company Pixar which has made films such as Toy Story. In 1996 " +
                "he returned to Apple and became CEO in 1997. He became a director of Disney when " +
                "they bought Pixar in 2006.", null));
        category.getWord(0).setMemorized(true);
    }
}
