package com.example.alberto.popularmovies;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class testDBactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dbactivity);

        Intent intent = getIntent();
        ArrayList<Movie> movies = intent.getParcelableArrayListExtra("movie_list");
        TextView textView = findViewById(R.id.testTextView);

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            int id = movie.getId();
            String originalTitle = movie.getMovieTitle();
            String overview = movie.getMovieOverview();
            double userRating = movie.getUserRating();
            String releaseDate = movie.getReleaseDate();
            String substring = releaseDate.substring(0, 4);

            textView.append(id + originalTitle + overview + userRating + substring + "\n");

        }
    }
}
