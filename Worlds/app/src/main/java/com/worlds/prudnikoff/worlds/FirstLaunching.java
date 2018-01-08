package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.os.AsyncTask;


public class FirstLaunching extends AsyncTask<Context, String, String> {

    @Override
    protected String doInBackground(Context... params) {
        CategoriesData.createFirstLaunchCategories(params[0]);
        return null;
    }

}
