package com.example.tle.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        Movie movie = bundle.getParcelable("movie");
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
        Picasso.with(this).load(movie.getPosterURL()).into(imageView);
    }
}
