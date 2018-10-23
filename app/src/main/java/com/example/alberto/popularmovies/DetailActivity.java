package com.example.alberto.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView posterIv = findViewById(R.id.detail_poster_iv);
        TextView originalTitleTv = findViewById(R.id.original_title_tv);
        TextView overviewTv = findViewById(R.id.overview_tv);
        TextView userRatingTv = findViewById(R.id.user_rating_tv);
        TextView releaseDateTv = findViewById(R.id.release_date_tv);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");

        String poster = movie.getmMoviePoster();
        Picasso.get().load(poster).fit().into(posterIv);
        String originalTitle = movie.getmMovieTitle();
        String overview = movie.getmMovieOverview();
        double userRating = (double)movie.getmUserRating();
        String releaseDate = movie.getmReleaseDate();

        originalTitleTv.setText(originalTitle);
        overviewTv.setText(overview);
        userRatingTv.setText(String.valueOf(userRating));
        releaseDateTv.setText(releaseDate);


    }
}
