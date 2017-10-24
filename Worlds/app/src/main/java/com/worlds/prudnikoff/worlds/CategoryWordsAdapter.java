package com.worlds.prudnikoff.worlds;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;

class CategoryWordsAdapter extends RecyclerView.Adapter<CategoryWordsAdapter.WordHolder> {

    private ArrayList<WordModel> words;
    private ArrayList<WordModel> wordsCopy;

    CategoryWordsAdapter(ArrayList<WordModel> words) {
        this.words = words;
        wordsCopy = new ArrayList<>(words);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    @Override
    public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_word_view,
                parent, false);
        return new WordHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(WordHolder wordHolder, int position) {
        WordModel word = words.get(position);
        wordHolder.partOfSpeechTextView.setText("(" + word.getPartOfSpeech() + ")");
        wordHolder.definitionTextView.setText(word.getDefinition());
        wordHolder.headWordTextView.setText(prepare(word.getHeadWord()));
        wordHolder.isMemorized.setChecked(word.isMemorized());
        if (word.getExamples() != null && word.getExamples().length() > 0) {
            wordHolder.examplesTextView.setText(word.getExamples());
        } else wordHolder.examplesTextView.setText("-");
        if (word.getSynonyms() != null && word.getSynonyms().length() > 0) {
            wordHolder.synonymsTextView.setText(word.getSynonyms());
        } else wordHolder.synonymsTextView.setText("-");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private String prepare(String headword) {
        final int maxLength = 11;
        String prepared = "";
        String currentLine = "";
        String[] parts = headword.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (currentLine.length() > maxLength) {
                prepared += currentLine + "\n";
                currentLine = "";
            }
            currentLine += parts[i] + " ";
        }
        prepared += currentLine;
        return prepared;
    }

    class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {

        CardView cardView;
        TextView headWordTextView;
        TextView definitionTextView;
        TextView partOfSpeechTextView;
        TextView examplesTextView;
        TextView synonymsTextView;
        CheckBox isMemorized;

        WordHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.w_card_View);
            headWordTextView = (TextView)itemView.findViewById(R.id.w_headword_textView);
            definitionTextView = (TextView)itemView.findViewById(R.id.w_definition_textView);
            partOfSpeechTextView = (TextView)itemView.findViewById(R.id.w_partOfSpeech_textView);
            examplesTextView = (TextView)itemView.findViewById(R.id.w_examples_textView);
            synonymsTextView = (TextView)itemView.findViewById(R.id.w_synonyms_textView);
            isMemorized = (CheckBox)itemView.findViewById(R.id.w_memorized_checkBox);
            isMemorized.setOnCheckedChangeListener(this);
            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == cardView.getId()) {
                String textToPronounce = words.get(getAdapterPosition()).getHeadWord();
                TextPronunciation.pronounce(textToPronounce);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == cardView.getId()) {
                AppDialogs.showWordOptionsDialog(v.getContext(), words, getAdapterPosition());
            }
            return true;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == isMemorized.getId()) {
                words.get(getAdapterPosition()).setMemorized(isChecked);
            }
        }
    }
}
