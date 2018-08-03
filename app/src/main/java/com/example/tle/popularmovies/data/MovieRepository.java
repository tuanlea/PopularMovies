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
public class MovieRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> allFavoriteMovies;

    public MovieRepository(Application application){
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        movieDao = db.movieDao();
        allFavoriteMovies = movieDao.getFavoriteMovieList();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void insert(Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    public void remove(Movie movie) {
        new removeAsyncTask(movieDao).execute(movie);
    }

    static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao asyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.insert(movies[0]);
            return null;
        }
    }

    static class removeAsyncTask extends  AsyncTask<Movie, Void, Void> {
        private MovieDao asyncTaskDao;

        removeAsyncTask(MovieDao favoriteMovieDao) {
            asyncTaskDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            asyncTaskDao.remove(movies[0]);
            return null;
        }
    }
}
