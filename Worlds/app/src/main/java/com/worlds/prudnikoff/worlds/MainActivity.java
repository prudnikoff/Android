package com.worlds.prudnikoff.worlds;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static CategoriesAdapter adapter;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        TextPronunciation.prepare(getApplicationContext());
        CategoriesData.restoreState(MainActivity.this);
        setUpActions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (CategoriesData.getNumOfCategories() == 0) {
            Toast.makeText(getApplicationContext(), "Please, click plus button to add a new category",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        CategoriesData.saveCurrentState(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TextPronunciation.destroyTextToSpeech();
    }

    private void setUpActions() {
        //setting up the RecyclerView of categories
        RecyclerView categoriesRecyclerView = (RecyclerView)findViewById(R.id.categoriesRecyclerView);
        categoriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(layoutManager);
        //setting up OnItemTouchListener for RecyclerView items
        categoriesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                categoriesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override public void onItemClick(View view, int position) {
                CategoryModel category = CategoriesData.getCategory(position);
                goCategoryWordsActivity(category.getNameOfCategory(), position, false, null);
            }

            @Override public void onLongItemClick(View view, int position) {
                AppDialogs.showCategoryOptionsDialog(MainActivity.this, position);
            }

        })
        );

        adapter = new CategoriesAdapter(CategoriesData.getCategories());
        categoriesRecyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton mainFab = (FloatingActionButton) findViewById(R.id.main_fab);
        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDialogs.createCategoryDialog(MainActivity.this);
            }
        });

        categoriesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    mainFab.hide();
                else if (dy < 0)
                    mainFab.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void goInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // setting up an application search
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.mainSearch_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final Suggestions querySuggestions = new Suggestions(MainActivity.this);
        final CursorAdapter suggestionAdapter = new SimpleCursorAdapter(this,
                R.layout.search_suggestion,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);

        searchView.setSuggestionsAdapter(suggestionAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {

            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchQuery = querySuggestions.getSuggestions().get(position);
                searchView.setQuery(searchQuery, true);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                if (searchQuery.endsWith(")")) {
                    String headword = query.substring(0, query.indexOf("(") - 1);
                    String categoryName = query.substring(query.indexOf("(") + 1, query.length() - 1);
                    goCategoryWordsActivity(categoryName, CategoriesData
                            .getCategoryPosition(categoryName), true, headword);
                    searchView.setQuery("", false);
                } else new InternetConnection(MainActivity.this, searchQuery).execute();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> suggestions = querySuggestions.getSuggestions(newText);
                String[] columns = { BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA,
                };

                MatrixCursor cursor = new MatrixCursor(columns);

                for (int i = 0; i < suggestions.size(); i++) {
                    String[] tmp = {Integer.toString(i), suggestions.get(i), suggestions.get(i)};
                    cursor.addRow(tmp);
                }

                suggestionAdapter.swapCursor(cursor);
                return true;
            }
        });
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, query, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_about: goInfoActivity(); break;
            case R.id.nav_share: startShareIntent(); break;
            case R.id.back_up_data: CategoriesData.backUpData(MainActivity.this); break;
            case R.id.restore_data: CategoriesData.restoreData(MainActivity.this, "WorldsAppBackUp.txt");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goCategoryWordsActivity(String nameOfCategory, int position,
                                         boolean ifCategoryWord, String headword) {
        Intent intent = new Intent(this, CategoryWordsActivity.class);
        intent.putExtra("categoryPosition", position);
        intent.putExtra("ifCategoryWord", ifCategoryWord);
        intent.putExtra("headword", headword);
        intent.putExtra("nameOfCategory", nameOfCategory);
        startActivity(intent);
    }

    private void startShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Worlds App - the worlds of English in your phone!\nDownload it in Google Play: google.play.etc";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Worlds App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void notifyAboutCategoriesChanging() {
        adapter.notifyDataSetChanged();
    }
}
