package com.example.alberto.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView posterIv = findViewById(R.id.detail_poster_iv);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        String poster = movie.getmMoviePoster();
        Picasso.get().load(poster).into(posterIv);




    }
}
