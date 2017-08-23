package com.worlds.prudnikoff.worlds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class WordsSlideFragment extends Fragment {

    private String partOfSpeech;
    private String definition;
    private String headWord;

    public static WordsSlideFragment getWordFragment(DefinitionModel word) {
        WordsSlideFragment fragment = new WordsSlideFragment();
        Bundle args = new Bundle();
        args.putString("partOfSpeech", word.getPartOfSpeech());
        args.putString("headWord", word.getHeadWord());
        args.putString("definition", word.getDefinition());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        partOfSpeech = args.getString("partOfSpeech");
        headWord = args.getString("headWord");
        definition = args.getString("definition");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(
                R.layout.card_quiz, container, false);
        TextView partOfSpeechTextView = (TextView)view.findViewById(R.id.card_quiz_partOfSpeech_textView);
        TextView headWordTextView = (TextView)view.findViewById(R.id.card_quiz_headword_textView);
        TextView definitionTextView = (TextView)view.findViewById(R.id.card_quiz_definition_textView);
        CardView cardView = (CardView)view.findViewById(R.id.card_quiz_card_View);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView definitionTextView = (TextView)v.findViewById(R.id.card_quiz_definition_textView);
                if (definitionTextView.getVisibility() == View.GONE) {
                    definitionTextView.setVisibility(View.VISIBLE);
                    definitionTextView.startAnimation(AnimationUtils.loadAnimation(v.getContext(),
                            android.R.anim.slide_in_left));
                }
            }
        });
        if (partOfSpeech == null) partOfSpeechTextView.setVisibility(View.GONE);
        partOfSpeechTextView.setText(partOfSpeech);
        headWordTextView.setText(headWord);
        definitionTextView.setText(definition);
        definitionTextView.setVisibility(View.GONE);
        return view;
    }
}
