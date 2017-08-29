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
        if (word.getPartOfSpeech() != null && word.getPartOfSpeech().length() > 0) {
            wordHolder.partOfSpeechTextView.setText("(" + word.getPartOfSpeech() + ")");
        } else wordHolder.partOfSpeechTextView.setText("");
        if (word.getDefinition() != null) {
            wordHolder.definitionTextView.setText(word.getDefinition());
        } else wordHolder.definitionTextView.setText("-");
        if (word.getHeadWord() != null) {
            wordHolder.headWordTextView.setText(word.getHeadWord());
        } else wordHolder.headWordTextView.setText("-");
        if (word.getExample() != null) {
            wordHolder.exampleTextView.setText(word.getExample());
        } else wordHolder.exampleTextView.setText("-");
        wordHolder.isMemorized.setChecked(word.isMemorized());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {

        CardView cardView;
        TextView headWordTextView;
        TextView definitionTextView;
        TextView partOfSpeechTextView;
        TextView exampleTextView;
        CheckBox isMemorized;

        WordHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.w_card_View);
            headWordTextView = (TextView)itemView.findViewById(R.id.w_headword_textView);
            definitionTextView = (TextView)itemView.findViewById(R.id.w_definition_textView);
            partOfSpeechTextView = (TextView)itemView.findViewById(R.id.w_partOfSpeech_textView);
            exampleTextView = (TextView)itemView.findViewById(R.id.w_example_textView);
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
