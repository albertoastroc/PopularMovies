package com.example.alberto.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "aa7c95df42b9e34788ea40bcfb3d83c9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Movie movie1 = new Movie("spiderman");
        Movie movie2 = new Movie("batman");
        Movie movie3 = new Movie("the matrix");
        Movie movie4 = new Movie("blade");
        Movie movie5 = new Movie("shadow");



        ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        movieArrayList.add(movie1);
        movieArrayList.add(movie2);
        movieArrayList.add(movie3);
        movieArrayList.add(movie4);
        movieArrayList.add(movie5);


        GridView gridView = findViewById(R.id.grid_view);
        MovieAdapter movieAdapter = new MovieAdapter(this, android.R.layout.simple_list_item_1, movieArrayList);
        gridView.setAdapter(movieAdapter);


    }

    private void loadImages(){



    }
}
