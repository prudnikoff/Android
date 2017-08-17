package com.worlds.prudnikoff.worlds;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryWordsActivity extends AppCompatActivity {

    private static CategoryWordsAdapter adapter;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                    }
                }, "com.google.android.tts");
        textToSpeech.setLanguage(Locale.UK);
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

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            textToSpeech.speak(textToPronounce, TextToSpeech.QUEUE_FLUSH, null, null);
                        }else{
                            textToSpeech.speak(textToPronounce, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    }

                    @Override public void onLongItemClick(View view, int position) {

                        AppDialogs.showWordOptionsDialog(view.getContext(),
                                getIntent().getExtras().getInt("categoryPosition"), position);

                    }

                })
        );


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

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
