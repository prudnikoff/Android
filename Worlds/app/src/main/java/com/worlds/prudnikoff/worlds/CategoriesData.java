package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    static void setCategories(ArrayList<CategoryModel> mCategories) {
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

    static void saveCurrentState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context
                .getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(nameToSave, gson.toJson(categories));
        editor.apply();
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
            int length = (int)backUp.length();
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
        }finally{
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e("Restore", "Something wrong with IO");
            }
        }
    }
}
