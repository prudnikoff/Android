package com.worlds.prudnikoff.worlds;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InternetConnection extends AsyncTask<String, String, String> {

    private JSONObject root = null;

    //private static final String DIRECT_URL_START = "http://owlbot.info/api/v1/dictionary/";
    //private static final String DIRECT_URL_END = "?format=json";
    private static final String DIRECT_URL_START = "https://api.pearson.com/v2/dictionaries/ldoce5/entries?headword=";
    private static final String DIRECT_URL_END = "&apikey=7drciBe92KwDL2ukNdkq0YpbWJmUhPhg";

    @Override
    protected String doInBackground(String... params) {
        try {
            root = getJSONObjectFromURL(params[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        parseJSON();
    }

    private static JSONObject getJSONObjectFromURL(String query) throws IOException, JSONException {

        String urlString = DIRECT_URL_START + query;
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

    private void parseJSON() {
        try {
            JSONArray rootArray = root.getJSONArray("results");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
