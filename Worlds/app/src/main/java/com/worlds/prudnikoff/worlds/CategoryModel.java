package com.worlds.prudnikoff.worlds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

class CategoryModel implements Serializable {

    private String nameOfCategory;
    private String dateAndTime;
    private ArrayList<WordModel> words;
    private ArrayList<WordModel> wordsToQuiz;
    private boolean isTop;

    CategoryModel(String nameOfCategory, String dateAndTime) {
        this.nameOfCategory = nameOfCategory;
        this.dateAndTime = dateAndTime;
        words = new ArrayList<>();
        wordsToQuiz = new ArrayList<>();
    }

    void changeTop() {
        if (isTop) {
            isTop = false;
        } else  isTop = true;
    }

    boolean isTop() {
        return isTop;
    }

    void addWord(WordModel word) {
        words.add(0, word);
    }

    void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    int getNumOfWords() {
        return words.size();
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

    WordModel getWord(int position) {
        return words.get(position);
    }

    int getWordPosition(String headword) {
        for (int i = 0; i < this.getNumOfWords(); i++) {
            if (this.getWord(i).getHeadWord().equals(headword)) {
                return i;
            }
        }
        return 0;
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
