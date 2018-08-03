package com.example.tle.popularmovies.detail;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tle.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TrailerTaskHandler implements com.example.tle.popularmovies.main.TaskHandler {

    private Context context;
    private MovieDetailActivity movieDatailActivity;

    TrailerTaskHandler(Context context, MovieDetailActivity movieDetailActivity) {
        this.context = context;
        this.movieDatailActivity = movieDetailActivity;
    }

    @Override
    public void handleTaskResponse(String json, IOException e) {
        if (e != null) {
            Toast.makeText(context,
                    "Failed to get trailers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        List<Trailer> trailers = new ArrayList<>();
        try {
            trailers = getTrailersFromJson(json);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
            Log.d("Handle review task", "get review failed with json exception",
                    jsonException);
        }

        // set trailers to views
        movieDatailActivity.setTrailersToView(trailers);
    }

    private List<Trailer> getTrailersFromJson(String json) throws JSONException {
        List<Trailer> trailers = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray results = obj.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject reviewObj = results.getJSONObject(i);
            String id = reviewObj.getString("id");
            String isoOne = reviewObj.getString("iso_639_1");
            String isoTwo = reviewObj.getString("iso_3166_1");
            String key = reviewObj.getString("key");
            String name = reviewObj.getString("name");
            String site = reviewObj.getString("site");
            String size = reviewObj.getString("size");
            String type = reviewObj.getString("type");

            Trailer trailer = new Trailer();
            trailer.setId(id);
            trailer.setIsoOne(isoOne);
            trailer.setIsoTwo(isoTwo);
            trailer.setKey(key);
            trailer.setName(name);
            trailer.setSite(site);
            trailer.setSize(size);
            trailer.setType(type);

            trailers.add(trailer);
        }
        return trailers;
    }
}
