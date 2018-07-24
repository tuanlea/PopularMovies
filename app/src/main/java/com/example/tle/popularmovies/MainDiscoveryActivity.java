package com.example.tle.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainDiscoveryActivity extends AppCompatActivity implements TaskHandler {
    String SORT_POPULARITY = "popularity.desc";
    String SORT_RATING = "vote_average.desc";
    ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);

        getPopularMovies();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_discovery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.popularity:
                getPopularMovies();
                return true;
            case R.id.rating:
                getHighestRatedMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void getHighestRatedMovies() {
        RetrieveMoviesTask retrieveMoviesTask = new RetrieveMoviesTask();
        retrieveMoviesTask.taskHandler = this;
        try {
            retrieveMoviesTask.execute(NetworkUtils.buildUrl(SORT_RATING));
        } catch (MalformedURLException e) {
            Toast.makeText(getApplicationContext(), "Malformed URL", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPopularMovies() {
        RetrieveMoviesTask retrieveMoviesTask = new RetrieveMoviesTask();
        retrieveMoviesTask.taskHandler = this;
        try {
            retrieveMoviesTask.execute(NetworkUtils.buildUrl(SORT_POPULARITY));
        } catch (MalformedURLException e) {
            Toast.makeText(getApplicationContext(), "Malformed URL", Toast.LENGTH_SHORT).show();
        }
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
            bindMoviesToView();
        } catch (JSONException e) {
            Log.e("handleTaskResponse", "json exception", e);
            Toast.makeText(this, "JSON error", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindMoviesToView() {
        GridView gridView = findViewById(R.id.gridview);
        MoviesAdapter moviesAdapter = new MoviesAdapter(this, movies);
        gridView.setAdapter(moviesAdapter);

        gridView.setOnItemClickListener(getGridViewOnItemClickListener());
    }

    private void parseMovies(String json) throws JSONException {
        movies = new ArrayList<>();
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