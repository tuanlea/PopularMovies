package com.example.tle.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tle.popularmovies.data.FavoriteMovie;
import com.example.tle.popularmovies.data.FavoriteMovieRepository;

import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FavoriteMovieListAdapter adapter = new FavoriteMovieListAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FavoriteMovieViewModel favoriteMovieViewModel
                = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovies().observe(this,
                new Observer<List<FavoriteMovie>>() {
                    @Override
                    public void onChanged(@Nullable List<FavoriteMovie> favoriteMovies) {
                        adapter.setAllFavoriteMovies(favoriteMovies);
                    }
                });
    }
}
