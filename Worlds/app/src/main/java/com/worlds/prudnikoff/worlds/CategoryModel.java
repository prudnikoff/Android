package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryModel implements Serializable {

    private String nameOfCategory;
    private String dateAndTime;
    private ArrayList<DefinitionModel> words;

    public CategoryModel(String nameOfCategory, String dateAndTime) {

        this.nameOfCategory = nameOfCategory;
        this.dateAndTime = dateAndTime;
        words = new ArrayList<>();

    }

    public void addWord(DefinitionModel word) {

        words.add(word);

    }

    public void setNameOfCategory(String nameOfCategory) {

        this.nameOfCategory = nameOfCategory;

    }

    public String getNumOfWords() {

        return String.valueOf(words.size());

    }

    public String getNameOfCategory() {

        return nameOfCategory;

    }

    public String getDateAndTime() {

        return dateAndTime;

    }

    public ArrayList<DefinitionModel> getWords() {

        return words;

    }

    public DefinitionModel getWordByPosition(int position) {

        return words.get(position);

    }

}
