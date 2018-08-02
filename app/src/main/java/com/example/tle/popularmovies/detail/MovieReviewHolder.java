package com.example.tle.popularmovies.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tle.popularmovies.R;

class MovieReviewHolder extends RecyclerView.ViewHolder {
    TextView authorTv;
    TextView contentTv;

    public MovieReviewHolder(View itemView) {
        super(itemView);
        authorTv = itemView.findViewById(R.id.author_tv);
        contentTv = itemView.findViewById(R.id.content_tv);
    }
}
