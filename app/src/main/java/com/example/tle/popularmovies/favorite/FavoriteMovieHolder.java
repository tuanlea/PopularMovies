package com.example.tle.popularmovies.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.tle.popularmovies.R;

class FavoriteMovieHolder extends RecyclerView.ViewHolder {
    public ImageView favoriteMovieImageIv;

    FavoriteMovieHolder(View itemView) {
        super(itemView);
        favoriteMovieImageIv = itemView.findViewById(R.id.favorite_movie_image);
    }
}
