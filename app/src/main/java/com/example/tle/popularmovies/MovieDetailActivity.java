package com.example.tle.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tle.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        movie = bundle.getParcelable("movie");
        if (movie == null) {
            return;
        }
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText(movie.getTitle());
        TextView releaseDateTv = findViewById(R.id.release_date_tv);
        releaseDateTv.setText(movie.getReleaseDate());
        TextView voteAverageTv = findViewById(R.id.vote_average_tv);
        voteAverageTv.setText(movie.getVoteAverage());
        TextView overviewTv = findViewById(R.id.overview_tv);
        overviewTv.setText(movie.getOverview());

        ImageView imageView = findViewById(R.id.image_iv);
        imageView.setAdjustViewBounds(true);
        Picasso.with(this).load(movie.getPosterPath()).into(imageView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),
                "Added as favorite.", Toast.LENGTH_LONG).show();
        FavoriteMovieViewModel favoriteMovieViewModel
                = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.insert(movie);
    }
}
