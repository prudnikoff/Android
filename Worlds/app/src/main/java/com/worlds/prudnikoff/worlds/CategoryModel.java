package com.worlds.prudnikoff.worlds;

import java.util.ArrayList;

public class CategoryModel {

    private String nameOfCategory;
    private String dateAndTime;
    private int numOfWords;
    private ArrayList<DefinitionModel> definitions;

    public CategoryModel(String nameOfCategory, String dateAndTime) {
        this.nameOfCategory = nameOfCategory;
        this.dateAndTime = dateAndTime;
        numOfWords = 0;
    }

    public void addDefinition(DefinitionModel definition) {
        definitions.add(definition);
        numOfWords++;
    }

    public int getNumOfWords() {
        return numOfWords;
    }

    public String getNameOfCategory() {
        return nameOfCategory;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public ArrayList<DefinitionModel> getDefinitions() {
        return definitions;
    }
}
