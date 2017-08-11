package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryModel implements Serializable {

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

    public String getNumOfWords() {
        return String.valueOf(numOfWords);
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
