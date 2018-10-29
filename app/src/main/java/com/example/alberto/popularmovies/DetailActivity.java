package com.example.alberto.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        Movie movie = intent.getParcelableExtra(MainActivity.MOVIE_EXTRA);
        String poster = movie.getMoviePoster();

        int targetHeight = 350;
        int targetWidth = 275;
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.ic_cloud_off_black_24dp)
                .resize(targetWidth, targetHeight)
                .into(posterIv);

        String originalTitle = movie.getMovieTitle();
        String overview = movie.getMovieOverview();
        double userRating = movie.getUserRating();
        String releaseDate = movie.getReleaseDate();
        String substring = releaseDate.substring(0, 4);

        originalTitleTv.setText(originalTitle);
        overviewTv.setText(overview);
        userRatingTv.setText(String.format("%s/10", String.valueOf(userRating)));
        releaseDateTv.setText(substring);


    }
}
