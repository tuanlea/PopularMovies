package com.example.tle.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tle.popularmovies.data.MovieRepository;
import com.example.tle.popularmovies.model.Movie;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> allFavoriteMovies;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allFavoriteMovies = movieRepository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void insert(Movie movie) {
        movieRepository.insert(movie);
    }

    public void remove(Movie movie) {
        movieRepository.remove(movie);
    }
}
