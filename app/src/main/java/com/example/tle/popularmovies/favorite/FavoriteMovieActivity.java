package com.example.tle.popularmovies.favorite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tle.popularmovies.detail.MovieDetailActivity;
import com.example.tle.popularmovies.R;
import com.example.tle.popularmovies.model.Movie;
import com.example.tle.popularmovies.ui.FavoriteMovieViewModel;

import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity implements OnFavoriteMovieClickHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FavoriteMovieListAdapter adapter =
                new FavoriteMovieListAdapter(getApplicationContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        FavoriteMovieViewModel favoriteMovieViewModel
                = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this,
                new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> favoriteMovies) {
                        adapter.setAllFavoriteMovies(favoriteMovies);
                    }
                });
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
}
