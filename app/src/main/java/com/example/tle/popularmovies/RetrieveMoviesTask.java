package com.example.tle.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Param, Progress, Result
 */
public class RetrieveMoviesTask extends AsyncTask<URL, Void, String> {

    TaskHandler taskHandler;
    private IOException e;

    @Override
    protected String doInBackground(URL... urls) {
        try {
            return NetworkUtils.getResponseFromHttpUrl(urls[0]);
        } catch (IOException e) {
            Log.e("handleDoInBackGround", "io exception", e);
            this.e = e;
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        taskHandler.handleTaskResponse(s, e);
    }
}
