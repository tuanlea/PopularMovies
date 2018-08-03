package com.example.tle.popularmovies.detail;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tle.popularmovies.model.TaskHandler;
import com.example.tle.popularmovies.util.NetworkUtils;

import java.io.IOException;
import java.net.URL;

// Param, Progress, Result
public class RetrieveJsonTask extends AsyncTask<URL, Void, String> {
    public TaskHandler taskHandler;
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
