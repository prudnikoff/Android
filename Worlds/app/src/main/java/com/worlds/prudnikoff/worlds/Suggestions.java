package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.jar.Pack200;

class Suggestions {

    private String query;
    private ArrayList<String> suggestions;
    private int mid;
    private static boolean isLoaded;
    private static ArrayList<String> wordsList;
    private final int SUGGESTIONS_MAX_SIZE = 10;

    Suggestions() {
        suggestions = new ArrayList<>();
    }

    static void prepare(Context context) {
        wordsList = new Database(context).getWordsList();
        isLoaded = true;
    }

    boolean getIsLoaded() {
        return isLoaded;
    }

    ArrayList<String> getSuggestions(String query) {
        this.query = query;
        suggestions.clear();
        for (int i = 0; i < CategoriesData.getNumOfCategories(); i++) {
            CategoryModel category = CategoriesData.getCategory(i);
            for (int j = 0; j < category.getNumOfWords(); j++) {
                String wordStr = category.getWord(j).getHeadWord();
                if (wordStr.toLowerCase().startsWith(query.toLowerCase())) {
                    suggestions.add(wordStr + " (" + category.getNameOfCategory() + ")");
                }
            }
        }
        if (isLoaded && query.length() > 1 && suggestions.size() <= SUGGESTIONS_MAX_SIZE) wordsListSearch();
        return suggestions;
    }

    ArrayList<String> getSuggestions() {
        return suggestions;
    }

    private void wordsListSearch() {
        mid = 0;
        binarySearch(0, wordsList.size() - 1);
        if (mid > 0) {
            int i = mid;
            while (i > 0 && wordsList.get(i - 1).toLowerCase().startsWith(query.toLowerCase())) {
                i--;
            }
            boolean isStarts = true;
            while ((suggestions.size() <= SUGGESTIONS_MAX_SIZE) && isStarts) {
                String word = wordsList.get(i);
                isStarts = word.toLowerCase().startsWith(query.toLowerCase());
                if ((!suggestions.contains(word)) && isStarts)
                    suggestions.add(word);
                i++;
            }
        }
    }

    private void binarySearch(int i, int j) {
        try {
            int center = (i + j) / 2;
            String word = wordsList.get(center);
            if (word.toLowerCase().startsWith(query.toLowerCase())) {
                mid = center;
            } else if (Math.abs(j - i) > 1) {
                int c = word.compareToIgnoreCase(query);
                if (c > 0) {
                    binarySearch(i, center);
                } else binarySearch(center, j);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
