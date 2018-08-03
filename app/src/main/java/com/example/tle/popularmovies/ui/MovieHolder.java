package com.example.tle.popularmovies.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.tle.popularmovies.R;

class MovieHolder extends RecyclerView.ViewHolder {
    public ImageView movieImageIv;

    MovieHolder(View itemView) {
        super(itemView);
        movieImageIv = itemView.findViewById(R.id.movie_image);
    }
}
