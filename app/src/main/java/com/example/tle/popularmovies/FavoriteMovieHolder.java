package com.example.tle.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class FavoriteMovieHolder extends RecyclerView.ViewHolder {
    public TextView favoriteMovieTitleTv;

    public FavoriteMovieHolder(View itemView) {
        super(itemView);
        favoriteMovieTitleTv = itemView.findViewById(R.id.favorite_movie_title);
    }
}
