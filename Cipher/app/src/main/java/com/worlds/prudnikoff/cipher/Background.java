package com.worlds.prudnikoff.cipher;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

class Background extends AsyncTask<String, String, String> {

    private ProgressDialog progressDialog;
    private Cipher cipher;
    private Context context;

    Background(Context context, Cipher cipher) {
        this.cipher = cipher;
        this.context = context;
        progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        MainActivity.getCurrentFragment().addKeyLog(cipher.getLog());
        Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        cipher.crypt();
        return null;
    }
}
