package com.example.tle.popularmovies;

import android.view.View;

import com.example.tle.popularmovies.model.Movie;

public interface OnRecyclerItemClickHandler {
    void handleRecylerItemClick(Movie current);
}
