package com.example.tle.popularmovies.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tle.popularmovies.R;
import com.example.tle.popularmovies.model.Trailer;

import java.util.List;

class TrailerListAdapter extends RecyclerView.Adapter<TrailerHolder> {
    private LayoutInflater layoutInflater;
    private List<Trailer> trailers;
    OnTrailerClickHandler onTrailerClickHandler;

    public TrailerListAdapter(Context applicationContext, MovieDetailActivity movieDetailActivity) {
        layoutInflater = LayoutInflater.from(applicationContext);
        onTrailerClickHandler = movieDetailActivity;
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
        final Trailer trailer = this.trailers.get(position);
        holder.idTv.setText("Trailer:" + trailer.getName());
        holder.idTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTrailerClickHandler.handleTrailerRecylerItemClick(trailer);
            }
        });
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
