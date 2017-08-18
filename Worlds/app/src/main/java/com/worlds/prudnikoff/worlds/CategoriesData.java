package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class CategoriesData implements Serializable {

    private static final String nameToSave = "categories";
    private static ArrayList<CategoryModel> categories = new ArrayList<>();

    public static int getNumOfCategories() {

        return categories.size();

    }

    public static ArrayList<CategoryModel> getCategories() {

        return categories;

    }

    public static void setCategories(ArrayList<CategoryModel> mCategories) {

        categories = mCategories;

    }

    public static CategoryModel getCategoryByPosition(int position) {

        return categories.get(position);

    }

    public static void deleteCategory(int position) {

        categories.remove(position);

    }

    public static void createNewCategory(Context context, String nameOfCategory) {

        String name = nameOfCategory.replaceAll(" ", "");

        if (name.length() > 0) {
            String currentDateAndTime = java.text.DateFormat.getDateTimeInstance()
                    .format(Calendar.getInstance().getTime());
            CategoryModel newCategory = new CategoryModel(nameOfCategory, currentDateAndTime);
            categories.add(0, newCategory);
            MainActivity.notifyAboutCategoriesChanging();
        } else Toast.makeText(context, "Sorry, the field cant't be empty", Toast.LENGTH_LONG).show();

    }

    public static String[] getStringListOfCategories() {

        String[] listOfCategories = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            listOfCategories[i] = categories.get(i).getNameOfCategory();
        }
        return listOfCategories;

    }

    public static void saveCurrentState(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context
                .getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(nameToSave, gson.toJson(CategoriesData.getCategories()));
        editor.apply();

    }

    public static void restoreState(Context context) {

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

}
