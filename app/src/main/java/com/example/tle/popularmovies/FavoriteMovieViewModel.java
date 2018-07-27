package com.example.tle.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tle.popularmovies.data.FavoriteMovieRepository;
import com.example.tle.popularmovies.model.Movie;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {
    private FavoriteMovieRepository favoriteMovieRepository;
    private LiveData<List<Movie>> allFavoriteMovies;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        favoriteMovieRepository = new FavoriteMovieRepository(application);
        allFavoriteMovies = favoriteMovieRepository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void insert(Movie movie) {
        favoriteMovieRepository.insert(movie);
    }

    public void remove(Movie movie) {
        favoriteMovieRepository.remove(movie);
    }
}
