package com.example.tle.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.tle.popularmovies.model.Movie;

import java.util.List;

/**
 * A Repository manages query threads and allows you to use multiple backends.
 * In the most common example, the Repository implements the logic for deciding whether to fetch
 * data from a network or use results cached in a local database.
 */
public class FavoriteMovieRepository {
    private FavoriteMovieDao favoriteMovieDao;
    private LiveData<List<Movie>> allFavoriteMovies;

    public FavoriteMovieRepository(Application application){
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        favoriteMovieDao = db.favoriteMovieDao();
        allFavoriteMovies = favoriteMovieDao.getFavoriteMovieList();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void insert(Movie movie) {
        new insertAsyncTask(favoriteMovieDao).execute(movie);
    }

    static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {
        private FavoriteMovieDao asyncTaskDao;

        insertAsyncTask(FavoriteMovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.insert(movies[0]);
            return null;
        }
    }
}
