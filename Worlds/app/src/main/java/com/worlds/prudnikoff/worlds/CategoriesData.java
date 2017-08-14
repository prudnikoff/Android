package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoriesData implements Serializable {

    private static ArrayList<CategoryModel> categories = new ArrayList<>();


    public static void addCategory(CategoryModel category){
        categories.add(category);
    }

    public static int getNumOfCategories() {
        return categories.size();
    }

    public static ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public static CategoryModel getCategoryByPosition(int position) {
        return categories.get(position);
    }

    public static String[] getStringListOfCategories() {
        String[] listOfCategories = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            listOfCategories[i] = categories.get(i).getNameOfCategory();
        }
        return listOfCategories;
    }
}
