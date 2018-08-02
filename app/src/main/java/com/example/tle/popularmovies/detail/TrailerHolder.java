package com.example.tle.popularmovies.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tle.popularmovies.R;

class TrailerHolder extends RecyclerView.ViewHolder {
    public TextView idTv;

    public TrailerHolder(View itemView) {
        super(itemView);
        idTv = itemView.findViewById(R.id.trailer_tv);
    }
}
