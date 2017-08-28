package com.worlds.prudnikoff.worlds;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WordsSlideQuizActivity extends AppCompatActivity {
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private int categoryPosition;
    private boolean byWords;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private int numOfQuizWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_slide_quiz_activity);
        progressBar = (ProgressBar)findViewById(R.id.quiz_progressBar);
        progressTextView = (TextView)findViewById(R.id.quiz_progress_textView);
        categoryPosition = getIntent().getIntExtra("categoryPosition", 0);
        byWords = getIntent().getBooleanExtra("byWords", true);
        boolean notMemorized = getIntent().getBooleanExtra("notMemorized", true);
        CategoriesData.getCategoryByPosition(categoryPosition).prepareToQuiz(notMemorized);
        numOfQuizWords = CategoriesData.getCategoryByPosition(categoryPosition).getWordsToQuiz().size();
        if (numOfQuizWords == 0) {
            Toast.makeText(WordsSlideQuizActivity.this, "Sorry, there are no words to start a quiz",
                    Toast.LENGTH_SHORT).show();
            progressTextView.setText("0/0");
        }
        progressBar.setMax(numOfQuizWords);
        String categoryName = CategoriesData.getCategoryByPosition(categoryPosition).getNameOfCategory();
        setTitle("Quiz: " + categoryName);
        // Instantiate a ViewPager and a PagerAdapter.
        ViewPager mPager = (ViewPager) findViewById(R.id.viewPager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                progressBar.setProgress(position + 1);
                progressTextView.setText(String.valueOf(position + 1) + "/" + String.valueOf(numOfQuizWords));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CategoriesData.saveCurrentState(WordsSlideQuizActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home: finish(); break;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int wordPosition) {
            return WordsSlideFragment.getWordFragment(categoryPosition, wordPosition, byWords);
        }

        @Override
        public int getCount() {
            return CategoriesData.getCategoryByPosition(categoryPosition).getWordsToQuiz().size();
        }
    }
}


