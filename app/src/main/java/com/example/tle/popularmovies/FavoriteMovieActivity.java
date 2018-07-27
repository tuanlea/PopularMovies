package com.example.tle.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tle.popularmovies.model.Movie;

import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FavoriteMovieListAdapter adapter = new FavoriteMovieListAdapter(getApplicationContext());
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

}
