package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Suggestions {

    private String query;
    private ArrayList<String> suggestions;
    private Context context;
    private final int SUGGESTIONS_MAX_SIZE = 10;

    Suggestions(Context context) {
        this.context = context;
        suggestions = new ArrayList<>();
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
        if (query.length() > 1 && suggestions.size() < SUGGESTIONS_MAX_SIZE) allWordsSearch();
        return suggestions;
    }

    private void allWordsSearch() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(query.substring(0, 1)
                            .toUpperCase() + " Words.txt")));
            String line;
            while ((line = reader.readLine()) != null && suggestions.size() < SUGGESTIONS_MAX_SIZE) {
                if (line.toLowerCase().startsWith(query.toLowerCase())) {
                    suggestions.add(line);
                }
            }
        } catch (IOException e) {
            Log.e("IOException", "Something wrong with the words database");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("IOException", "Something wrong with the words database");
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
