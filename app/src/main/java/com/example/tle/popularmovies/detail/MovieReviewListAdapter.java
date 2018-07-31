package com.example.tle.popularmovies.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tle.popularmovies.R;
import com.example.tle.popularmovies.model.MovieReview;

import java.util.List;

class MovieReviewListAdapter extends RecyclerView.Adapter<MovieReviewHolder> {

    private LayoutInflater layoutInflater;
    private List<MovieReview> movieReviews;

    public MovieReviewListAdapter(Context context) {
        super();
        layoutInflater = LayoutInflater.from(context);
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    @NonNull
    @Override
    public MovieReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                layoutInflater.inflate(R.layout.movie_review_item, parent, false);
        return new MovieReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewHolder holder, int position) {
        holder.authorTv.setText(this.movieReviews.get(position).getAuthor());
        holder.contentTv.setText(this.movieReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (movieReviews == null) {
            return 0;
        }
        return movieReviews.size();
    }
}
