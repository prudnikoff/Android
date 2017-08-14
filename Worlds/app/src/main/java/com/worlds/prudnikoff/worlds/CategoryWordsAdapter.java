package com.worlds.prudnikoff.worlds;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryWordsAdapter extends RecyclerView.Adapter<CategoryWordsAdapter.WordHolder> {

    private ArrayList<DefinitionModel> words;

    public CategoryWordsAdapter(ArrayList<DefinitionModel> words) {
        this.words = words;
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    @Override
    public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_view,
                parent, false);
        return new WordHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(WordHolder wordHolder, int position) {
        DefinitionModel word = words.get(position);
        if (word.getPartOfSpeech() != null) {
            wordHolder.partOfSpeechTextView.setText("(" + word.getPartOfSpeech() + ")");
        } else wordHolder.partOfSpeechTextView.setText("");
        if (word.getAmericanPronunciations() != null) {
            wordHolder.americanPronunciationTextView.setText("[ " + word.getAmericanPronunciations() + " ]");
        } else wordHolder.americanPronunciationTextView.setText("-");
        if (word.getBritishPronunciations() != null) {
            wordHolder.britishPronunciationTextView.setText("[ " + word.getBritishPronunciations() + " ]");
        } else wordHolder.britishPronunciationTextView.setText("-");
        if (word.getDefinition() != null) {
            wordHolder.definitionTextView.setText(word.getDefinition());
        } else wordHolder.definitionTextView.setText("-");
        if (word.getHeadWord() != null) {
            wordHolder.headWordTextView.setText(word.getHeadWord());
        } else wordHolder.headWordTextView.setText("-");
        if (word.getExample() != null) {
            wordHolder.exampleTextView.setText(word.getExample());
        } else wordHolder.exampleTextView.setText("-");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class WordHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView headWordTextView;
        TextView definitionTextView;
        TextView partOfSpeechTextView;
        TextView britishPronunciationTextView;
        TextView americanPronunciationTextView;
        TextView exampleTextView;

        WordHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.w_card_View);
            headWordTextView = (TextView)itemView.findViewById(R.id.w_headword_textView);
            definitionTextView = (TextView)itemView.findViewById(R.id.w_definition_textView);
            partOfSpeechTextView = (TextView)itemView.findViewById(R.id.w_partOfSpeech_textView);
            britishPronunciationTextView = (TextView)itemView.findViewById(R.id.w_british_pronunciation_textView);
            americanPronunciationTextView = (TextView)itemView.findViewById(R.id.w_american_pronunciation_textView);
            exampleTextView = (TextView)itemView.findViewById(R.id.w_example_textView);
        }

    }
}
