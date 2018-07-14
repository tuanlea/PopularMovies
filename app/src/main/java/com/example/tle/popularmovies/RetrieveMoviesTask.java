package com.example.tle.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Param, Progress, Result
 */
public class RetrieveMoviesTask extends AsyncTask<URL, Void, String> {

    TaskResponseHandler taskResponseHandler;

    @Override
    protected String doInBackground(URL... urls) {
        try {
            return NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());
        } catch (IOException e) {
            Log.e("RetrieveMoviesTask", "io exception", e);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        taskResponseHandler.handleTaskResponse(s);
    }
}
