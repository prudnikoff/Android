package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;

class CategoryModel implements Serializable {

    private String nameOfCategory;
    private String dateAndTime;
    private ArrayList<DefinitionModel> words;

    CategoryModel(String nameOfCategory, String dateAndTime) {
        this.nameOfCategory = nameOfCategory;
        this.dateAndTime = dateAndTime;
        words = new ArrayList<>();
    }

    void addWord(DefinitionModel word) {
        words.add(0, word);
    }

    void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    String getNumOfWords() {
        return String.valueOf(words.size());
    }

    String getNameOfCategory() {
        return nameOfCategory;
    }

    String getDateAndTime() {
        return dateAndTime;
    }

    ArrayList<DefinitionModel> getWords() {
        return words;
    }

    DefinitionModel getWordByPosition(int position) {
        return words.get(position);
    }

}
