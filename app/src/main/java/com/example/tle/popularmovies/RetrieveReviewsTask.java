package com.example.tle.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

// Param, Progress, Result
class RetrieveReviewsTask extends AsyncTask<URL, Void, String> {
    TaskHandler taskHandler;
    private IOException ioException;

    @Override
    protected String doInBackground(URL... urls) {
        try {
            return NetworkUtils.getResponseFromHttpUrl(urls[0]);
        } catch (IOException e) {
            ioException = e;
            Log.d("Error", "failed to get http response for review", e);
        }
        return "";
    }


    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        taskHandler.handleTaskResponse(json, ioException);
    }
}
