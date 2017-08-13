package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoriesData implements Serializable {

    ArrayList<CategoryModel> categories;

    public CategoriesData() {
        categories = new ArrayList<>();
    }

    public void addCategory(CategoryModel category){
        categories.add(category);
    }

    public int getNumOfCategories() {
        return categories.size();
    }

    public ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public CategoryModel getCategoryByPosition(int position) {
        return categories.get(position);
    }

    public String[] getStringListOfCategories() {
        String[] listOfCategories = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            listOfCategories[i] = categories.get(i).getNameOfCategory();
        }
        return listOfCategories;
    }
}
