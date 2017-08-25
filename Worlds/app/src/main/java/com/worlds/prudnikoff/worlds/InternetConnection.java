package com.worlds.prudnikoff.worlds;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class InternetConnection extends AsyncTask<String, Integer, JSONObject> {

    private ProgressDialog progressDialog;
    private Context context;
    private String searchQuery;
    private static final String DIRECT_URL_START = "https://api.pearson.com/v2/dictionaries" +
            "/ldoce5/entries?headword=";
    private static final String DIRECT_URL_END = "&apikey=7drciBe92KwDL2ukNdkq0YpbWJmUhPhg";

    InternetConnection(Context context, String searchQuery) {
        this.context = context;
        this.searchQuery = searchQuery.replaceAll(" ", ",");
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject root = null;
        try {
            root = getJSONObjectFromURL();
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
        progressDialog.dismiss();
    }

    private JSONObject getJSONObjectFromURL() throws IOException, JSONException {
        String urlString = DIRECT_URL_START + searchQuery + DIRECT_URL_END;
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
        return new JSONObject(jsonString);
    }

    private void parseJSON(JSONObject root) {
        try {
            JSONArray resultsArray = root.getJSONArray("results");
            ArrayList<WordModel> definitions = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject definitionElem = resultsArray.getJSONObject(i);
                if (definitionElem.has("headword") && !definitionElem.isNull("senses")) {
                    JSONObject sensesObj = definitionElem.getJSONArray("senses").getJSONObject(0);
                    if (sensesObj.has("definition") || sensesObj.has("signpost")) {
                        String headword = definitionElem.getString("headword");
                        String partOfSpeech = null;
                        if (definitionElem.has("part_of_speech")) {
                            partOfSpeech = definitionElem.getString("part_of_speech");
                        }
                        String definition = null;
                        String example = null;
                        if (sensesObj.has("definition")) {
                            definition = sensesObj.getJSONArray("definition").getString(0);
                        } else if (sensesObj.has("signpost")) {
                            definition = sensesObj.getString("signpost");
                        }
                        if (sensesObj.has("examples")) {
                            example = sensesObj.getJSONArray("examples").getJSONObject(0)
                                    .getString("text");
                        }
                        WordModel wordModel = new WordModel(partOfSpeech, headword,
                                definition, example);
                        definitions.add(wordModel);
                    }
                }
            }
            if (definitions.isEmpty()) {
                Toast.makeText(context, "Sorry, the word wasn't found",
                        Toast.LENGTH_SHORT).show();
            } else goInternetDefinitionsActivity(definitions);
        } catch (JSONException ex) {
            Log.e("JSON", "Something wrong with JSON parsing");
            ex.printStackTrace();
        } catch (IndexOutOfBoundsException indexOutEx) {
            Log.e("JSON", "IndexOutOfBoundsException");
            indexOutEx.printStackTrace();
        } catch (NullPointerException nullPointerEx) {
            Log.e("Internet", "Internet connection failed");
            Toast.makeText(context, "Internet connection failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void goInternetDefinitionsActivity(ArrayList<WordModel> definitions) {
        Intent intent = new Intent(context, InternetDefinitionsActivity.class);
        intent.putExtra("query", searchQuery.replaceAll(",", " "));
        intent.putExtra("definitions", definitions);
        context.startActivity(intent);
    }

}
