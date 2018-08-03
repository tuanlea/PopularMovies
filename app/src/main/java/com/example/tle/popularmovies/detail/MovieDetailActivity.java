package com.example.tle.popularmovies.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tle.popularmovies.R;
import com.example.tle.popularmovies.model.Movie;
import com.example.tle.popularmovies.model.MovieReview;
import com.example.tle.popularmovies.model.Trailer;
import com.example.tle.popularmovies.ui.FavoriteMovieViewModel;
import com.example.tle.popularmovies.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity
        implements View.OnClickListener, OnTrailerClickHandler {

    Movie movie;

    FloatingActionButton fab;
    List<Movie> allFavoriteMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = getMovie();
        setMovieToView();

        retrieveMovieReviews();
        retrieveTrailers();

        getAllFavoriteMovies();

        // Floating action button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private Movie getMovie() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return null;
        }
        return bundle.getParcelable("movie");
    }

    private void retrieveTrailers() {
        try {
            String trailerPath = movie.getId() + "/videos";
            URL url = NetworkUtils.buildUrl(trailerPath);

            RetrieveJsonTask retrieveJsonTask = new RetrieveJsonTask();
            retrieveJsonTask.taskHandler = new TrailerTaskHandler(getApplicationContext(),
                    this);
            retrieveJsonTask.execute(url);
        } catch (MalformedURLException e) {
            Log.e("Movie detail", "malformed url", e);
            Toast.makeText(getApplicationContext(), "Error getting trailers",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllFavoriteMovies() {
        FavoriteMovieViewModel favoriteMovieViewModel =
                ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this, getObserver());
    }

    @NonNull
    private Observer<List<Movie>> getObserver() {
        return new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> allFavoriteMovies) {
                setAllFavoriteMovies(allFavoriteMovies);
                toggleFab(isFavorite(movie));
            }
        };
    }

    public void setAllFavoriteMovies(List<Movie> allFavoriteMovies) {
        this.allFavoriteMovies = allFavoriteMovies;
    }

    private void setMovieToView() {
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

    /// movie/{id}/reviews
    public void retrieveMovieReviews() {
        try {
            String reviewPath = movie.getId() + "/reviews";
            URL url = NetworkUtils.buildUrl(reviewPath);

            RetrieveJsonTask retrieveJsonTask = new RetrieveJsonTask();
            retrieveJsonTask.taskHandler = new ReviewTaskHandler(getApplicationContext(),
                    this);
            retrieveJsonTask.execute(url);
        } catch (MalformedURLException e) {
            Log.e("Movie detail", "malformed url", e);
            Toast.makeText(getApplicationContext(), "Error getting reviews",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void setReviewsToView(List<MovieReview> movieReviews) {
        RecyclerView recyclerView = findViewById(R.id.movie_reviews_rv);
        final MovieReviewListAdapter adapter =
                new MovieReviewListAdapter(getApplicationContext());
        adapter.setMovieReviews(movieReviews);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    public void setTrailersToView(List<Trailer> trailers) {
        RecyclerView recyclerView = findViewById(R.id.movie_trailers_rv);
        TrailerListAdapter adapter = new TrailerListAdapter(getApplicationContext(), this);
        adapter.setTrailers(trailers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void handleTrailerRecylerItemClick(Trailer trailer) {
        Toast.makeText(getApplicationContext(), "this trailer: " + trailer.getId(),
                Toast.LENGTH_SHORT).show();
        String key = trailer.getKey();
        String youtubeBaseURL = "https://www.youtube.com/watch?v=" + key;
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            getApplicationContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            getApplicationContext().startActivity(webIntent);
        }
    }
}
