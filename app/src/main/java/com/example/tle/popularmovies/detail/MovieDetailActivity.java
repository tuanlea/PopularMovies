package com.example.tle.popularmovies.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tle.popularmovies.R;
import com.example.tle.popularmovies.main.TaskHandler;
import com.example.tle.popularmovies.model.Movie;
import com.example.tle.popularmovies.model.MovieReview;
import com.example.tle.popularmovies.ui.FavoriteMovieViewModel;
import com.example.tle.popularmovies.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity
        implements TaskHandler, View.OnClickListener {

    Movie movie;

    FloatingActionButton fab;
    List<Movie> allFavoriteMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        movie = bundle.getParcelable("movie");
        if (movie == null) {
            return;
        }
        setMovieToView(movie);
        retrieveMovieReviews();

        getAllFavoriteMovies();

        // Floating action button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void getAllFavoriteMovies() {
        FavoriteMovieViewModel favoriteMovieViewModel =
                ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this,
                new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> allFavoriteMovies) {
                        setAllFavoriteMovies(allFavoriteMovies);
                    }
                });
    }

    private void setReviewsToView(List<MovieReview> movieReviews) {
        RecyclerView recyclerView = findViewById(R.id.movie_reviews_rv);
        final MovieReviewListAdapter adapter =
                new MovieReviewListAdapter(getApplicationContext());
        adapter.setMovieReviews(movieReviews);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void setMovieToView(Movie movie) {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText(movie.getTitle());
        TextView releaseDateTv = findViewById(R.id.release_date_tv);
        releaseDateTv.setText(movie.getReleaseDate());
        TextView voteAverageTv = findViewById(R.id.vote_average_tv);
        voteAverageTv.setText(movie.getVoteAverage());
        TextView overviewTv = findViewById(R.id.overview_tv);
        overviewTv.setText(movie.getOverview());

        ImageView imageView = findViewById(R.id.image_iv);
        imageView.setAdjustViewBounds(true);
        Picasso.with(this).load(movie.getPosterPathFull()).into(imageView);
    }

    private boolean isFavorite(Movie movie) {
        if (allFavoriteMovies == null) {
            return false;
        }
        for (Movie favoriteMovie : allFavoriteMovies) {
           if (movie.getId().equals(favoriteMovie.getId())) {
               return true;
           }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        FavoriteMovieViewModel favoriteMovieViewModel
                = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        if (!isFavorite(movie)) {
            Toast.makeText(getApplicationContext(),
                    "Added as favorite.", Toast.LENGTH_SHORT).show();
            favoriteMovieViewModel.insert(movie);
            toggleFab(true);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Removed as favorite.", Toast.LENGTH_SHORT).show();
            favoriteMovieViewModel.remove(movie);
            toggleFab(false);
        }
    }

    private void toggleFab(boolean isFavorite) {
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }

    public void setAllFavoriteMovies(List<Movie> allFavoriteMovies) {
        this.allFavoriteMovies = allFavoriteMovies;
        toggleFab(isFavorite(movie));
    }

    /// movie/{id}/reviews
    public void retrieveMovieReviews() {
        try {
            String reviewPath = movie.getId() + "/reviews";
            URL url = NetworkUtils.buildUrl(reviewPath);

            RetrieveReviewsTask retrieveReviewsTask = new RetrieveReviewsTask();
            retrieveReviewsTask.taskHandler = this;
            retrieveReviewsTask.execute(url);
        } catch (MalformedURLException e) {
            Log.e("Movie detail", "malformed url", e);
            Toast.makeText(getApplicationContext(), "Error getting reviews", Toast.LENGTH_SHORT)
                .show();
        }
    }

    @Override
    public void handleTaskResponse(String json, IOException e) {
        if (e != null) {
            Toast.makeText(getApplicationContext(), "Failed to get movie reviews.",
                    Toast.LENGTH_SHORT);
            return;
        }
        List<MovieReview> movieReviews = new ArrayList<>();
        try {
            movieReviews = getReviewsFromJson(json);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
            Log.d("Handle review task", "get review failed with json exception", e );
        }

        setReviewsToView(movieReviews);
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
