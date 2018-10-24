package com.example.alberto.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "aa7c95df42b9e34788ea40bcfb3d83c9";
    private final String templateRequest = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg";
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected()){
            new DownloadListTask().execute("popular");
        } else setContentView(R.layout.error);


    }

    private boolean isConnected(){

        boolean isConnected;
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO make menu appear below appbar

        if (isConnected()){
            int id = item.getItemId();
            switch (id) {

                case R.id.popular:
                    setContentView(R.layout.activity_main);
                    new DownloadListTask().execute("popular");
                    return true;

                case R.id.top_rated:
                    setContentView(R.layout.activity_main);
                    new DownloadListTask().execute("top_rated");
                    return true;
            }
        } else setContentView(R.layout.error);


        return super.onOptionsItemSelected(item);
    }

    private class DownloadListTask extends AsyncTask<String, Void, ArrayList<Movie>> {

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
                String responseString = null;
                if (response != null) {
                    responseString = response.body().string();
                }
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
            recyclerView.setHasFixedSize(true);
            RecyclerViewMovieAdapter recyclerViewMovieAdapter = new RecyclerViewMovieAdapter(getApplicationContext(), movies, new RecyclerViewMovieAdapter.ItemClickListener() {
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
}
