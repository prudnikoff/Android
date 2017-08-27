package com.worlds.prudnikoff.worlds;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

class Suggestions {

    private String query;
    private ArrayList<String> suggestions;
    private Context context;

    Suggestions(Context context) {
        this.context = context;
        suggestions = new ArrayList<>();
    }

    ArrayList<String> getSuggestions(String query) {
        this.query = query;
        suggestions.clear();
        for (int i = 0; i < CategoriesData.getNumOfCategories(); i++) {
            CategoryModel category = CategoriesData.getCategoryByPosition(i);
            for (int j = 0; j < category.getNumOfWords(); j++) {
                String wordStr = category.getWordByPosition(j).getHeadWord();
                if (wordStr.toLowerCase().startsWith(query.toLowerCase())) {
                    suggestions.add(wordStr);
                }
            }
        }
        if (query.length() > 1 && suggestions.size() < 5) allWordsSearch();
        return suggestions;
    }

    private void allWordsSearch() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("words.txt")));
            String line;
            while ((line = reader.readLine()) != null && suggestions.size() < 5) {
                if (line.toLowerCase().startsWith(query.toLowerCase())) {
                    suggestions.add(line);
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    String getQuery() {
        return query;
    }

    ArrayList<String> getSuggestions() {
        return suggestions;
    }
}
