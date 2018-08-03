package com.example.tle.popularmovies.main;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tle.popularmovies.main.TaskHandler;
import com.example.tle.popularmovies.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Param, Progress, Result
 */
public class RetrieveMoviesTask extends AsyncTask<URL, Void, String> {

    public TaskHandler taskHandler;
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
