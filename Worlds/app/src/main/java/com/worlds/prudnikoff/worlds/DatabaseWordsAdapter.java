package com.worlds.prudnikoff.worlds;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

class DatabaseWordsAdapter extends RecyclerView.Adapter<DatabaseWordsAdapter.WordHolder> {

    private ArrayList<WordModel> words;

    DatabaseWordsAdapter(ArrayList<WordModel> words) {
        this.words = words;
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    @Override
    public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.database_word_view,
                parent, false);
        return new WordHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(WordHolder wordHolder, int position) {
        WordModel word = words.get(position);
        wordHolder.partOfSpeechTextView.setText("(" + word.getPartOfSpeech() + ")");
        wordHolder.definitionTextView.setText(word.getDefinition());
        wordHolder.headWordTextView.setText(word.getHeadWord());
        if (!word.getExample().equals("null")) {
            wordHolder.exampleTextView.setText(word.getExample());
        } else wordHolder.exampleTextView.setText("-");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView headWordTextView;
        TextView definitionTextView;
        TextView partOfSpeechTextView;
        TextView exampleTextView;
        TextView exampleLabelTextView;
        ImageButton addButton;

        WordHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_View);
            headWordTextView = (TextView)itemView.findViewById(R.id.headword_textView);
            definitionTextView = (TextView)itemView.findViewById(R.id.definition_textView);
            partOfSpeechTextView = (TextView)itemView.findViewById(R.id.partOfSpeech_textView);
            exampleTextView = (TextView)itemView.findViewById(R.id.example_textView);
            exampleLabelTextView = (TextView)itemView.findViewById(R.id.example_label_textView);
            addButton = (ImageButton)itemView.findViewById(R.id.add_button);
            cardView.setOnClickListener(this);
            addButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            WordModel definition = words.get(getAdapterPosition());
            if (view.getId() == addButton.getId()) {
                AppDialogs.addWordToCategoryDialog(view.getContext(), definition);
            } else if (view.getId() == cardView.getId()) {
                TextPronunciation.pronounce(definition.getHeadWord());
            }
        }
    }
}
