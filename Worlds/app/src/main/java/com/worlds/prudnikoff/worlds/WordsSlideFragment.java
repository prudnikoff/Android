package com.worlds.prudnikoff.worlds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class WordsSlideFragment extends Fragment {

    private String partOfSpeech;
    private String definition;
    private String headWord;
    private int categoryPosition;
    private int wordPosition;
    private boolean byWords;
    private boolean isMemorized;

    public static WordsSlideFragment getWordFragment(int categoryPosition, int wordPosition,
                                                     boolean byWords) {
        WordsSlideFragment fragment = new WordsSlideFragment();
        Bundle args = new Bundle();
        WordModel word = CategoriesData.getCategoryByPosition(categoryPosition)
                .getWordsToQuiz().get(wordPosition);
        args.putString("partOfSpeech", word.getPartOfSpeech());
        args.putString("headWord", word.getHeadWord());
        args.putString("definition", word.getDefinition());
        args.putBoolean("isMemorized", word.isMemorized());
        args.putBoolean("byWords", byWords);
        args.putInt("wordPosition", wordPosition);
        args.putInt("categoryPosition", categoryPosition);
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
        byWords = args.getBoolean("byWords");
        isMemorized = args.getBoolean("isMemorized");
        categoryPosition = args.getInt("categoryPosition");
        wordPosition = args.getInt("wordPosition");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(
                R.layout.card_quiz, container, false);
        TextView partOfSpeechTextView = (TextView)view.findViewById(R.id.card_quiz_partOfSpeech_textView);
        TextView headWordTextView = (TextView)view.findViewById(R.id.card_quiz_headword_textView);
        TextView headWord = (TextView)view.findViewById(R.id.card_quiz_definition_textView);
        CardView cardView = (CardView)view.findViewById(R.id.card_quiz_card_View);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.card_quiz_memorized_checkBox);
        if (partOfSpeech == null) partOfSpeechTextView.setVisibility(View.GONE);
        partOfSpeechTextView.setText(partOfSpeech);
        headWordTextView.setText(this.headWord);
        headWord.setText(definition);
        if (byWords) {
            headWord.setVisibility(View.GONE);
        } else headWordTextView.setVisibility(View.GONE);
        checkBox.setChecked(isMemorized);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (byWords) {
                    TextView definitionTextView = (TextView) v.findViewById(R.id.card_quiz_definition_textView);
                    if (definitionTextView.getVisibility() == View.GONE) {
                        definitionTextView.setVisibility(View.VISIBLE);
                        definitionTextView.startAnimation(AnimationUtils.loadAnimation(v.getContext(),
                                android.R.anim.slide_in_left));
                    }
                } else {
                    TextView headWord = (TextView) v.findViewById(R.id.card_quiz_headword_textView);
                    if (headWord.getVisibility() == View.GONE) {
                        headWord.setVisibility(View.VISIBLE);
                        headWord.startAnimation(AnimationUtils.loadAnimation(v.getContext(),
                                android.R.anim.slide_in_left));
                    }
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CategoriesData.getCategoryByPosition(categoryPosition).getWordsToQuiz()
                        .get(wordPosition).setMemorized(isChecked);
                CategoryWordsActivity.notifyAboutWordsChanging();
            }
        });
        return view;
    }
}
