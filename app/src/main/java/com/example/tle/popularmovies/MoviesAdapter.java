package com.example.tle.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Movie> movies;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
       this.context = context;
       this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(movies.get(position).getPosterURL()).into(imageView);
        return imageView;
    }
}
