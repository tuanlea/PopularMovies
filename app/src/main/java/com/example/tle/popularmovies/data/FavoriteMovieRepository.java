package com.example.tle.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * A Repository manages query threads and allows you to use multiple backends.
 * In the most common example, the Repository implements the logic for deciding whether to fetch
 * data from a network or use results cached in a local database.
 */
public class FavoriteMovieRepository {
    private FavoriteMovieDao favoriteMovieDao;
    private LiveData<List<FavoriteMovie>> allFavoriteMovies;

    public FavoriteMovieRepository(Application application){
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        favoriteMovieDao = db.favoriteMovieDao();
        allFavoriteMovies = favoriteMovieDao.getFavoriteMovieList();
    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void insert(FavoriteMovie favoriteMovie) {
        new insertAsyncTask(favoriteMovieDao).execute(favoriteMovie);
    }

    static class insertAsyncTask extends AsyncTask<FavoriteMovie, Void, Void> {
        private FavoriteMovieDao asyncTaskDao;

        insertAsyncTask(FavoriteMovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            asyncTaskDao.insert(favoriteMovies[0]);
            return null;
        }
    }
}
