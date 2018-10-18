package com.example.alberto.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "aa7c95df42b9e34788ea40bcfb3d83c9";
    private final String templateRequest = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg";

    RecyclerViewMovieAdapter recyclerViewMovieAdapter;

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
        protected void onPostExecute(ArrayList<Movie> movies) {

            RecyclerView recyclerView = findViewById(R.id.main_rv);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            recyclerViewMovieAdapter = new RecyclerViewMovieAdapter(getApplicationContext(), movies, new RecyclerViewMovieAdapter.ItemClickListener() {
                @Override
                public void onItemClick(Movie item) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("movie", item);
                    startActivity(intent);
                }
            }

            );
            recyclerView.setAdapter(recyclerViewMovieAdapter);
            recyclerView.setClickable(true);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){

            case R.id.popular:
                new DownloadListTask().execute("popular");
                return true;

            case R.id.top_rated:
                new DownloadListTask().execute("top_rated");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
