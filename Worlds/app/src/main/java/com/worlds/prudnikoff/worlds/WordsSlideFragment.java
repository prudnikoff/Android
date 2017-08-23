package com.worlds.prudnikoff.worlds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = inflater.inflate(
                R.layout.card_quiz, container, false);
        TextView partOfSpeechTextView = (TextView)view.findViewById(R.id.card_quiz_partOfSpeech_textView);
        TextView headWordTextView = (TextView)view.findViewById(R.id.card_quiz_headword_textView);
        TextView definitionTextView = (TextView)view.findViewById(R.id.card_quiz_definition_textView);
        partOfSpeechTextView.setText(partOfSpeech);
        headWordTextView.setText(headWord);
        definitionTextView.setText(definition);
        return view;
    }
}
