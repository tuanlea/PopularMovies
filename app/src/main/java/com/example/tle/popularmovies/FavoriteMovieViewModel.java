package com.example.tle.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tle.popularmovies.data.FavoriteMovie;
import com.example.tle.popularmovies.data.FavoriteMovieRepository;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {
    private FavoriteMovieRepository favoriteMovieRepository;
    private LiveData<List<FavoriteMovie>> allFavoriteMovies;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        favoriteMovieRepository = new FavoriteMovieRepository(application);
        allFavoriteMovies = favoriteMovieRepository.getAllFavoriteMovies();
    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void insert(FavoriteMovie favoriteMovie) {
        favoriteMovieRepository.insert(favoriteMovie);
    }

}
