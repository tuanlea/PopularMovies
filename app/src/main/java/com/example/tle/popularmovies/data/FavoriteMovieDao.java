package com.example.tle.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.tle.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void remove(Movie movie);

    @Query("DELETE FROM favorite_movie_table")
    void deleteAll();

    @Query("SELECT * FROM favorite_movie_table ORDER BY id ASC")
    LiveData<List<Movie>> getFavoriteMovieList();

}
