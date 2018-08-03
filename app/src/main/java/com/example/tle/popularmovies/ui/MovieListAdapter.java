package com.example.tle.popularmovies.ui;

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

public class MovieListAdapter extends RecyclerView.Adapter<MovieHolder> {
    private LayoutInflater layoutInflater;
    private List<Movie> allMovies;
    private OnMovieClickHandler onMovieClickHandler;

    public MovieListAdapter(Context context, OnMovieClickHandler onItemClickHandler) {
        layoutInflater = LayoutInflater.from(context);
        this.onMovieClickHandler = onItemClickHandler;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                layoutInflater.inflate(R.layout.movie_image_item, parent, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        if (allMovies != null) {
            final Movie current = allMovies.get(position);
            String posterPathFull = current.getPosterPathFull();
            Picasso.with(layoutInflater.getContext())
                    .load(posterPathFull)
                    .into(holder.movieImageIv);
            holder.movieImageIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMovieClickHandler.handleMovieRecylerItemClick(current);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (allMovies == null) {
            return 0;
        }
        return allMovies.size();
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
        notifyDataSetChanged();
    }
}
