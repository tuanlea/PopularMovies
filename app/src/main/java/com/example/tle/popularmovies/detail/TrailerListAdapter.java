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

class TrailerListAdapter extends RecyclerView.Adapter<TrailerHolder> {
    private LayoutInflater layoutInflater;
    private List<Trailer> trailers;

    public TrailerListAdapter(Context applicationContext) {
        layoutInflater = LayoutInflater.from(applicationContext);
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.movie_trailer_item, parent,
                false);
        return new TrailerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        holder.idTv.setText(this.trailers.get(position).getId());
    }

    @Override
    public int getItemCount() {
        if (trailers.isEmpty()) {
            return 0;
        }
        return trailers.size();
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
