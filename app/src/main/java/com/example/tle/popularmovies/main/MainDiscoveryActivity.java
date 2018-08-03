package com.example.tle.popularmovies.main;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tle.popularmovies.R;
import com.example.tle.popularmovies.detail.MovieDetailActivity;
import com.example.tle.popularmovies.favorite.FavoriteMovieActivity;
import com.example.tle.popularmovies.favorite.OnFavoriteMovieClickHandler;
import com.example.tle.popularmovies.model.Movie;
import com.example.tle.popularmovies.ui.MovieListAdapter;
import com.example.tle.popularmovies.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainDiscoveryActivity extends AppCompatActivity
        implements TaskHandler, OnFavoriteMovieClickHandler {
    String SORT_POPULAR = "popular";
    String SORT_RATING = "top_rated";
    ArrayList<Movie> movies = new ArrayList<>();

    private static final String MOVIES_STATE = "movie_state";
    private static final String QUERY_STATE = "query_state";
    private static final String VIEW_STATE = "view_state";

    RecyclerView recyclerView;
    MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);

        recyclerView = findViewById(R.id.main_movies_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new MovieListAdapter(getApplicationContext(), this);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            getMovies(SORT_POPULAR);
        }
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
                getMovies(SORT_POPULAR);
                return true;
            case R.id.rating:
                getMovies(SORT_RATING);
                return true;
            case R.id.favorite:
                startFavoriteActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startFavoriteActivity() {
        Intent intent = new Intent(getApplicationContext(), FavoriteMovieActivity.class);
        startActivity(intent);
    }

    private void getMovies(String sort) {
        RetrieveMoviesTask retrieveMoviesTask = new RetrieveMoviesTask();
        retrieveMoviesTask.taskHandler = this;
        try {
            retrieveMoviesTask.execute(NetworkUtils.buildUrl(sort));
        } catch (MalformedURLException e) {
            Toast.makeText(getApplicationContext(), "Malformed URL", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleTaskResponse(String json, IOException ex) {
        if (ex != null) {
            Toast.makeText(this, "Error retrieving movies data.", Toast.LENGTH_LONG).show();
        }
        try {
            parseMovies(json);
            bindMoviesToView();
        } catch (JSONException e) {
            Log.e("handleTaskResponse", "json exception", e);
            Toast.makeText(this, "Error retrieving movies data.", Toast.LENGTH_LONG).show();
        }
    }

    private void bindMoviesToView() {
        adapter.setAllFavoriteMovies(movies);
        adapter.notifyDataSetChanged();
    }

    private void parseMovies(String json) throws JSONException {
        movies = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray results = obj.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObj = results.getJSONObject(i);
            String id = movieObj.getString("id");
            String title = movieObj.getString("title");
            String releaseDate = movieObj.getString("release_date");
            String posterPath = movieObj.getString("poster_path");
            String voteAverage = movieObj.getString("vote_average");
            String overview = movieObj.getString("overview");

            Movie movie = new Movie();
            movie.setId(id);
            movie.setTitle(title);
            movie.setReleaseDate(releaseDate);
            movie.setPosterPath(posterPath);
            movie.setVoteAverage(voteAverage);
            movie.setOverview(overview);
            movies.add(movie);
        }
    }

    @Override
    public void handleFavoriteMovieRecylerItemClick(Movie movie) {
        startMovieDetailActivity(movie);
    }

    private void startMovieDetailActivity(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MOVIES_STATE, movies);

        Parcelable viewState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(VIEW_STATE, viewState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        movies = (ArrayList<Movie>) savedInstanceState.getSerializable(MOVIES_STATE);
        adapter.setAllFavoriteMovies(movies);

        Parcelable viewState = savedInstanceState.getParcelable(VIEW_STATE);
        recyclerView.getLayoutManager().onRestoreInstanceState(viewState);
    }
}