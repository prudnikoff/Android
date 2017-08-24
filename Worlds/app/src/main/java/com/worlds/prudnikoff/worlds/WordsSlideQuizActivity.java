package com.worlds.prudnikoff.worlds;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Collections;

public class WordsSlideQuizActivity extends AppCompatActivity {
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private ArrayList<WordModel> words;
    private ArrayList<Integer> randomIndexes;
    private int categoryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_slide_quiz_activity);
        categoryPosition = getIntent().getIntExtra("categoryPosition", 0);
        words = CategoriesData.getCategoryByPosition(categoryPosition).getWords();
        randomIndexes = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            randomIndexes.add(i);
        }
        Collections.shuffle(randomIndexes);
        String categoryName = CategoriesData.getCategoryByPosition(categoryPosition).getNameOfCategory();
        setTitle("Quiz: " + categoryName);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewPager);
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

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
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
        public Fragment getItem(int position) {
            int wordPosition = 0;
            for (int i = 0; i < randomIndexes.size(); i++) {
                if (randomIndexes.get(i) == position) {
                    wordPosition = i;
                }
            }
            return WordsSlideFragment.getWordFragment(categoryPosition, wordPosition);
        }

        @Override
        public int getCount() {
            return words.size();
        }
    }
}


