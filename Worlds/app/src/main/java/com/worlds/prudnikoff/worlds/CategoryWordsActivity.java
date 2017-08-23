package com.worlds.prudnikoff.worlds;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryWordsActivity extends AppCompatActivity {

    private static CategoryWordsAdapter adapter;
    private int numOfCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_words_activity);
        RecyclerView wordsRecyclerView = (RecyclerView)findViewById(R.id.category_words_recyclerView);
        wordsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wordsRecyclerView.setLayoutManager(layoutManager);
        final int categoryPosition = getIntent().getExtras().getInt("categoryPosition");
        numOfCategory = categoryPosition;
        ArrayList<DefinitionModel> words = CategoriesData.getCategoryByPosition(categoryPosition)
                .getWords();
        setTitle(getIntent().getExtras().getString("nameOfCategory"));
        adapter = new CategoryWordsAdapter(new ArrayList<>(words));
        wordsRecyclerView.setAdapter(adapter);
        wordsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                        wordsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        String textToPronounce = CategoriesData.getCategoryByPosition(categoryPosition)
                                .getWordByPosition(position).getHeadWord();
                        TextPronunciation.pronounce(textToPronounce);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        AppDialogs.showWordOptionsDialog(view.getContext(),
                                getIntent().getExtras().getInt("categoryPosition"), position);
                    }

                })
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_menu, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.categorySearch_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                adapter.filter(query);
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

    private void goQuizActivity() {
        Intent intent = new Intent(CategoryWordsActivity.this, WordsSlideQuizActivity.class);
        intent.putExtra("categoryPosition", numOfCategory);
        startActivity(intent);
    }
    public static void notifyAboutWordsChanging() {
        adapter.notifyDataSetChanged();
    }
}
