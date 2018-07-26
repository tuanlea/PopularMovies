package com.example.tle.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.tle.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    private static MovieRoomDatabase INSTANCE;

    /**
     * MovieRoomDatabase should be a singletone to prevent multiple intances of the database opened
     * at the same time
     */
    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database movie_database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();

}
