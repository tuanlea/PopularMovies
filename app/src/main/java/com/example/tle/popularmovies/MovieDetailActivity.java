package com.example.tle.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tle.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

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

        FavoriteMovieViewModel favoriteMovieViewModel =
                ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this,
                new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> allFavoriteMovies) {
                        setAllFavoriteMovies(allFavoriteMovies);
                    }
                });

        // Floating action button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
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
}
