package com.worlds.prudnikoff.worlds;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class CategoryWordsActivity extends AppCompatActivity {

    private static CategoryWordsAdapter adapter;
    private int numOfCategory;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_words_activity);
        final FloatingActionButton categoryFab = (FloatingActionButton)findViewById(R.id.category_fab);
        RecyclerView wordsRecyclerView = (RecyclerView)findViewById(R.id.category_words_recyclerView);
        wordsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wordsRecyclerView.setLayoutManager(layoutManager);
        final int categoryPosition = getIntent().getExtras().getInt("categoryPosition");
        int scrollPosition = 0;
        if (getIntent().getExtras().getBoolean("ifCategoryWord")) {
            scrollPosition = CategoriesData.getCategory(categoryPosition)
                    .getWordPosition(getIntent().getExtras().getString("headword"));
        };

        numOfCategory = categoryPosition;
        ArrayList<WordModel> words = CategoriesData.getCategory(categoryPosition)
                .getWords();
        setTitle(getIntent().getExtras().getString("nameOfCategory"));
        adapter = new CategoryWordsAdapter(words);
        wordsRecyclerView.setAdapter(adapter);
        wordsRecyclerView.scrollToPosition(scrollPosition);
        categoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDialogs.createNewWordDialog(v.getContext(),
                        CategoriesData.getCategory(categoryPosition));
            }
        });
        wordsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    categoryFab.hide();
                else if (dy < 0)
                    categoryFab.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        CategoriesData.saveCurrentState(CategoryWordsActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_menu, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.categorySearch_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final Suggestions querySuggestions = new Suggestions();
        final CursorAdapter suggestionAdapter = new SimpleCursorAdapter(this,
                R.layout.search_suggestion,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);

        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {

            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchQuery = querySuggestions.getSuggestions().get(position);
                searchView.setQuery(searchQuery, true);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                if (searchQuery.endsWith(")")) {
                    String headword = query.substring(0, query.indexOf("(") - 1);
                    String categoryName = query.substring(query.indexOf("(") + 1, query.length() - 1);
                    Intent intent = new Intent(CategoryWordsActivity.this, CategoryWordsActivity.class);
                    intent.putExtra("categoryPosition", CategoriesData.getCategoryPosition(categoryName));
                    intent.putExtra("ifCategoryWord", true);
                    intent.putExtra("headword", headword);
                    intent.putExtra("nameOfCategory", categoryName);
                    startActivity(intent);
                    searchView.setQuery("", false);
                } else goDatabaseWordsActivity(new Database(CategoryWordsActivity.this).getWordsByQuery(searchQuery));
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> suggestions = querySuggestions.getSuggestions(newText);
                String[] columns = { BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA,
                };

                MatrixCursor cursor = new MatrixCursor(columns);

                for (int i = 0; i < suggestions.size(); i++) {
                    String[] tmp = {Integer.toString(i), suggestions.get(i), suggestions.get(i)};
                    cursor.addRow(tmp);
                }

                suggestionAdapter.swapCursor(cursor);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home: finish(); break;
            case R.id.wordsCards_item: goQuizActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    void goDatabaseWordsActivity(ArrayList<WordModel> definitions) {
        Intent intent = new Intent(CategoryWordsActivity.this, DatabaseWordsActivity.class);
        intent.putExtra("query", searchQuery.replaceAll(",", " "));
        intent.putExtra("definitions", definitions);
        startActivity(intent);
    }

    private void goQuizActivity() {
        AppDialogs.chooseQuizOptionDialog(CategoryWordsActivity.this, numOfCategory);
    }

    public static void notifyAboutWordsChanging() {
        if (adapter != null) adapter.notifyDataSetChanged();
    }
}
