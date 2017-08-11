package com.worlds.prudnikoff.worlds;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DefinitionListAdapter extends RecyclerView.Adapter<DefinitionListAdapter.DefinitionHolder> {

    private ArrayList<DefinitionModel> definitions;

    public DefinitionListAdapter(ArrayList<DefinitionModel> definitions) {
        this.definitions = definitions;
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    @Override
    public DefinitionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_view,
                parent, false);
        return new DefinitionHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(DefinitionHolder definitionHolder, int position) {
        DefinitionModel definition = definitions.get(position);
        if (definition.getPartOfSpeech() != null) {
            definitionHolder.partOfSpeechTextView.setText("(" + definition.getPartOfSpeech() + ")");
        } else definitionHolder.partOfSpeechTextView.setText("");
        if (definition.getAmericanPronunciations() != null) {
            definitionHolder.americanPronunciationTextView.setText("[ " + definition.getAmericanPronunciations() + " ]");
        } else definitionHolder.americanPronunciationTextView.setText("-");
        if (definition.getBritishPronunciations() != null) {
            definitionHolder.britishPronunciationTextView.setText("[ " + definition.getBritishPronunciations() + " ]");
        } else definitionHolder.britishPronunciationTextView.setText("-");
        if (definition.getDefinition() != null) {
            definitionHolder.definitionTextView.setText(definition.getDefinition());
        } else definitionHolder.definitionTextView.setText("-");
        if (definition.getHeadWord() != null) {
            definitionHolder.headWordTextView.setText(definition.getHeadWord());
        } else definitionHolder.headWordTextView.setText("-");
        if (definition.getExample() != null) {
            definitionHolder.exampleTextView.setText(definition.getExample());
        } else definitionHolder.exampleTextView.setText("-");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class DefinitionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView headWordTextView;
        TextView definitionTextView;
        TextView partOfSpeechTextView;
        TextView britishPronunciationTextView;
        TextView americanPronunciationTextView;
        TextView exampleTextView;
        ImageButton addButton;

        DefinitionHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_View);
            headWordTextView = (TextView)itemView.findViewById(R.id.headword_textView);
            definitionTextView = (TextView)itemView.findViewById(R.id.definition_textView);
            partOfSpeechTextView = (TextView)itemView.findViewById(R.id.partOfSpeech_textView);
            britishPronunciationTextView = (TextView)itemView.findViewById(R.id.british_pronunciation_textView);
            americanPronunciationTextView = (TextView)itemView.findViewById(R.id.american_pronunciation_textView);
            exampleTextView = (TextView)itemView.findViewById(R.id.example_textView);
            addButton = (ImageButton)itemView.findViewById(R.id.add_button);
            addButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == addButton.getId()) {
                Toast.makeText(view.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
