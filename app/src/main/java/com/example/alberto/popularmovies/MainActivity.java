package com.example.alberto.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "aa7c95df42b9e34788ea40bcfb3d83c9";
    private final String templateRequest = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Movie movie1 = new Movie(templateRequest);
        Movie movie2 = new Movie(templateRequest);
        Movie movie3 = new Movie(templateRequest);
        Movie movie4 = new Movie(templateRequest);
        Movie movie5 = new Movie(templateRequest);


        ArrayList<Movie> movieArrayList1 = new ArrayList<Movie>();
        movieArrayList1.add(movie1);
        movieArrayList1.add(movie2);
        movieArrayList1.add(movie3);
        movieArrayList1.add(movie4);
        movieArrayList1.add(movie5);

        new DownloadListTask().execute("popular");

    }

    private class DownloadListTask extends AsyncTask<String,Void,ArrayList<Movie>> {

        ArrayList<Movie> movies = new ArrayList<>();

        @Override
        protected ArrayList<Movie> doInBackground(String... preference) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/" + preference[0] + "?page=1&language=en-US&api_key=" + API_KEY)
                    .get()
                    .build();

            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String responseString = response.body().string();
                movies = ParseJson.parseJson(responseString);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(final ArrayList<Movie> movies) {

            GridView gridView = findViewById(R.id.grid_view);
            MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, movies);
            gridView.setAdapter(movieAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("movie", movies.get(position));
                    startActivity(intent);

                }
            });

        }
    }
}
