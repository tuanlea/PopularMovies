package com.example.tle.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainDiscoveryActivity extends AppCompatActivity implements TaskResponseHandler {
    ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);

        RetrieveMoviesTask retrieveMoviesTask = new RetrieveMoviesTask();
        retrieveMoviesTask.taskResponseHandler = this;
        retrieveMoviesTask.execute(NetworkUtils.buildUrl());

        GridView gridView = findViewById(R.id.gridview);
        MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies);
        gridView.setAdapter(moviesAdapter);

        gridView.setOnItemClickListener(getGridViewOnItemClickListener());
    }

    private AdapterView.OnItemClickListener getGridViewOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startMovieDetailActivity(parent, view, position, id);
            }
        };
    }

    private void startMovieDetailActivity(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
        Movie movie = movies.get(position);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void handleTaskResponse(String json) {
        try {
            parseMovies(json);
        } catch (JSONException e) {
            Log.e("processFinish", "json exception", e);
        }
    }

    private void parseMovies(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray results = obj.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObj = results.getJSONObject(i);
            String title = movieObj.getString("title");
            String releaseDate = movieObj.getString("release_date");
            String posterPath = movieObj.getString("poster_path");
            String voteAverage = movieObj.getString("vote_average");
            String overview = movieObj.getString("overview");

            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setReleaseDate(releaseDate);
            movie.setPosterPath(posterPath);
            movie.setVoteAverage(voteAverage);
            movie.setOverview(overview);
            movies.add(movie);
        }
    }
}