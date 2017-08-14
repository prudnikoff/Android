package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryModel implements Serializable {

    private String nameOfCategory;
    private String dateAndTime;
    private ArrayList<DefinitionModel> definitions;

    public CategoryModel(String nameOfCategory, String dateAndTime) {
        this.nameOfCategory = nameOfCategory;
        this.dateAndTime = dateAndTime;
        definitions = new ArrayList<>();
    }

    public void addDefinition(DefinitionModel definition) {
        definitions.add(definition);
    }

    public void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    public String getNumOfWords() {
        return String.valueOf(definitions.size());
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
