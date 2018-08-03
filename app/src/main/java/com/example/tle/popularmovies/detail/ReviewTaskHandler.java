package com.example.tle.popularmovies.detail;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tle.popularmovies.model.MovieReview;
import com.example.tle.popularmovies.model.TaskHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ReviewTaskHandler implements TaskHandler {
    private Context context;
    private MovieDetailActivity movieDetailActivity;

    ReviewTaskHandler(Context context, MovieDetailActivity movieDetailActivity) {
        this.context = context;
        this.movieDetailActivity = movieDetailActivity;
    }

    @Override
    public void handleTaskResponse(String json, IOException e) {
        if (e != null) {
            Toast.makeText(context, "Failed to get movie reviews.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        List<MovieReview> movieReviews = new ArrayList<>();
        try {
            movieReviews = getReviewsFromJson(json);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
            Log.d("Handle review task", "get review failed with json exception",
                    jsonException);
        }

        movieDetailActivity.setReviewsToView(movieReviews);
    }

    private List<MovieReview> getReviewsFromJson(String json) throws JSONException {
        List<MovieReview> movieReviews = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray results = obj.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject reviewObj = results.getJSONObject(i);
            String id = reviewObj.getString("id");
            String author = reviewObj.getString("author");
            String content = reviewObj.getString("content");

            MovieReview movieReview = new MovieReview();
            movieReview.setId(id);
            movieReview.setAuthor(author);
            movieReview.setContent(content);

            movieReviews.add(movieReview);
        }
        return movieReviews;
    }

}
