package com.example.tle.popularmovies.favorite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tle.popularmovies.R;
import com.example.tle.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteMovieListAdapter extends RecyclerView.Adapter<FavoriteMovieHolder> {
    private LayoutInflater layoutInflater;
    private List<Movie> allFavoriteMovies;
    private OnFavoriteMovieClickHandler onFavoriteMovieClickHandler;

    public FavoriteMovieListAdapter(Context context, OnFavoriteMovieClickHandler onItemClickHandler) {
        layoutInflater = LayoutInflater.from(context);
        this.onFavoriteMovieClickHandler = onItemClickHandler;
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new FavoriteMovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieHolder holder, int position) {
        if (allFavoriteMovies != null) {
            final Movie current = allFavoriteMovies.get(position);
            String posterPathFull = current.getPosterPathFull();
            Picasso.with(layoutInflater.getContext())
                    .load(posterPathFull)
                    .into(holder.favoriteMovieImageIv);
            holder.favoriteMovieImageIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFavoriteMovieClickHandler.handleFavoriteMovieRecylerItemClick(current);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (allFavoriteMovies == null) {
            return 0;
        }
        return allFavoriteMovies.size();
    }

    public void setAllFavoriteMovies(List<Movie> allFavoriteMovies) {
        this.allFavoriteMovies = allFavoriteMovies;
        notifyDataSetChanged();
    }
}
