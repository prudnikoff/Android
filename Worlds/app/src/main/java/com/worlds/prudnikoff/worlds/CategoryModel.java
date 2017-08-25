package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

class CategoryModel implements Serializable {

    private String nameOfCategory;
    private String dateAndTime;
    private ArrayList<WordModel> words;
    private ArrayList<WordModel> wordsToQuiz;

    CategoryModel(String nameOfCategory, String dateAndTime) {
        this.nameOfCategory = nameOfCategory;
        this.dateAndTime = dateAndTime;
        words = new ArrayList<>();
        wordsToQuiz = new ArrayList<>();
    }

    void addWord(WordModel word) {
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

    ArrayList<WordModel> getWords() {
        return words;
    }

    WordModel getWordByPosition(int position) {
        return words.get(position);
    }

    void prepareToQuiz(boolean notMemorized) {
        wordsToQuiz.clear();
        for (int i = 0; i < words.size(); i++) {
            if (!notMemorized) {
                wordsToQuiz.add(i, words.get(i));
            } else if (!words.get(i).isMemorized()) {
                wordsToQuiz.add(words.get(i));
            }
        }
        Collections.shuffle(wordsToQuiz);
    }

    ArrayList<WordModel> getWordsToQuiz() {
        return wordsToQuiz;
    }
}
