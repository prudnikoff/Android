package com.worlds.prudnikoff.worlds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class CategoryWordsActivity extends AppCompatActivity {

    private static CategoryWordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_words_activity);
        RecyclerView wordsRecyclerView = (RecyclerView)findViewById(R.id.category_words_recyclerView);
        wordsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wordsRecyclerView.setLayoutManager(layoutManager);
        final int categoryPosition = getIntent().getExtras().getInt("categoryPosition");
        ArrayList<DefinitionModel> words = CategoriesData.getCategoryByPosition(categoryPosition)
                .getWords();
        setTitle(getIntent().getExtras().getString("nameOfCategory"));
        adapter = new CategoryWordsAdapter(words);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);

    }

    public static void notifyAboutWordsChanging() {

        adapter.notifyDataSetChanged();

    }

}
