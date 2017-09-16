package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.os.AsyncTask;

class BackgroundLoading extends AsyncTask<Context, String, String> {

    @Override
    protected String doInBackground(Context... params) {
        Suggestions.prepare(params[0]);
        return null;
    }
}
