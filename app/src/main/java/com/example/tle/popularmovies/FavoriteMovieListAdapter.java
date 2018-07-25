package com.example.tle.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tle.popularmovies.data.FavoriteMovie;

import java.util.List;

class FavoriteMovieListAdapter extends RecyclerView.Adapter<FavoriteMovieHolder> {
    LayoutInflater layoutInflater;
    List<FavoriteMovie> allFavoriteMovies;

    public FavoriteMovieListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new FavoriteMovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieHolder holder, int position) {
        if (allFavoriteMovies != null) {
            FavoriteMovie current = allFavoriteMovies.get(position);
            holder.favoriteMovieTitleTv.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.favoriteMovieTitleTv.setText("No Favorite Movies");
        }
    }

    @Override
    public int getItemCount() {
        if (allFavoriteMovies == null) {
            return 0;
        }
        return allFavoriteMovies.size();
    }

    public void setAllFavoriteMovies(List<FavoriteMovie> allFavoriteMovies) {
        this.allFavoriteMovies = allFavoriteMovies;
        notifyDataSetChanged();
    }
}
