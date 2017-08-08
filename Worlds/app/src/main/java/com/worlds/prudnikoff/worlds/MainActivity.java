package com.worlds.prudnikoff.worlds;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setUpActions();
    }

    private void setUpActions() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "To add a new category", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new InternetConnection().execute(query.replaceAll(" ", ","));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_about: goInfoActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class InternetConnection extends AsyncTask<String, Integer, JSONObject> {

        private static final String DIRECT_URL_START = "https://api.pearson.com/v2/dictionaries/ldoce5/entries?headword=";
        private static final String DIRECT_URL_END = "&apikey=7drciBe92KwDL2ukNdkq0YpbWJmUhPhg";

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject root = null;
            try {
                root = getJSONObjectFromURL(params[0]);
            } catch (IOException IOEx) {
                Log.e("IOException", "IO stream error");
                IOEx.printStackTrace();
            } catch (JSONException JSONEx) {
                Log.e("JSON", "Something wrong with converting String to JSON");
                JSONEx.printStackTrace();
            }
            return root;
        }

        @Override
        protected void onPostExecute(JSONObject root) {
            parseJSON(root);
        }

        private JSONObject getJSONObjectFromURL(String query) throws IOException, JSONException {

            String urlString = DIRECT_URL_START + query + DIRECT_URL_END;
            HttpURLConnection urlConnection;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();

            String jsonString = sb.toString();
            System.out.println("JSON: " + jsonString);
            return new JSONObject(jsonString);

        }
    }

    private void parseJSON(JSONObject root) {
        try {
            JSONArray resultsArray = root.getJSONArray("results");
            DefinitionModel[] definitions = new DefinitionModel[resultsArray.length()];
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject definitionElem = resultsArray.getJSONObject(i);
                String headword = definitionElem.getString("headword");
                String partOfSpeech = null;
                if (definitionElem.has("part_of_speech")) {
                    partOfSpeech = definitionElem.getString("part_of_speech");
                }
                String americanPronunciations = null;
                String britishPronunciations = null;
                if (definitionElem.has("pronunciations")) {
                    britishPronunciations = definitionElem
                            .getJSONArray("pronunciations")
                            .getJSONObject(0)
                            .getString("ipa");
                    if (definitionElem.getJSONArray("pronunciations").length() > 1) {
                        americanPronunciations = definitionElem
                                .getJSONArray("pronunciations")
                                .getJSONObject(1)
                                .getString("ipa");
                    }
                }
                String definition = null;
                String example = null;
                if (!definitionElem.isNull("senses")) {
                    JSONObject sensesObj = definitionElem.getJSONArray("senses").getJSONObject(0);
                    if (sensesObj.has("definition")) {
                        definition = sensesObj.getJSONArray("definition").getString(0);
                    } else if (sensesObj.has("signpost")) {
                        definition = sensesObj.getString("signpost");
                    }
                    if (sensesObj.has("examples")) {
                        example = sensesObj.getJSONArray("examples").getJSONObject(0).getString("text");
                    }
                }
                DefinitionModel definitionModel = new DefinitionModel(partOfSpeech, headword,
                        definition, example, americanPronunciations, britishPronunciations);
                definitions[i] = definitionModel;
            }
            WordModel word = new WordModel(definitions[0].getHeadWord(), definitions);
            goWordActivity(word);
        } catch (JSONException ex) {
            Log.e("JSON", "Something wrong with JSON parsing");
            ex.printStackTrace();
        } catch (IndexOutOfBoundsException indexOutEx) {
            Log.e("JSON", "IndexOutOfBoundsException");
            Toast.makeText(getApplicationContext(), "Sorry, the word wasn't found", Toast.LENGTH_SHORT).show();
            indexOutEx.printStackTrace();
        } catch (NullPointerException nullPointerEx) {
            Log.e("Internet", "Internet connection failed");
            Toast.makeText(getApplicationContext(), "Internet connection failed", Toast.LENGTH_SHORT).show();

        }
    }

    private void goWordActivity(WordModel word) {
        Intent intent = new Intent(this, WordActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }
}
