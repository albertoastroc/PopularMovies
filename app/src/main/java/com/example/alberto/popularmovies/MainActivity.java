package com.example.alberto.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.alberto.popularmovies.adapters.RecyclerViewMovieAdapter;
import com.example.alberto.popularmovies.database.MainViewModel;
import com.example.alberto.popularmovies.helpers.ParseJson;
import com.example.alberto.popularmovies.helpers.RequestQue;
import com.example.alberto.popularmovies.helpers.UrlBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie";
    public static final String TITLE_EXTRA = "title";
    public static final String ARRAY_LIST_EXTRA = "array_list";
    public static final String ID = "id";
    public static final String CLASS_NAME = MainActivity.class.getSimpleName();

    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected()){
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

        if (savedInstanceState != null){

            String savedTitle = savedInstanceState.getString(TITLE_EXTRA);
            movies = savedInstanceState.getParcelableArrayList(ARRAY_LIST_EXTRA);
            setTitle(savedTitle);
            setLayoutList(movies);

            MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    if (getTitle() == getString(R.string.my_favorites_title)) {
                        setLayoutList((ArrayList<Movie>) movies);
                        Log.d(CLASS_NAME, getString(R.string.populating_viewmodel));
                    }

                }
            });


        } else {
            getJson(UrlBuilder.listBuilder(getString(R.string.popular_option)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_EXTRA, String.valueOf(getTitle()));
        outState.putParcelableArrayList(ARRAY_LIST_EXTRA , movies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (isConnected() && id == R.id.popular){

            setTitle(getString(R.string.popular_title));
            getJson(UrlBuilder.listBuilder(getString(R.string.popular_option)));
            return true;

        } else if (isConnected() && id == R.id.top_rated){

            setTitle(getString(R.string.top_rated_title));
            getJson(UrlBuilder.listBuilder(getString(R.string.top_rated_option)));
            return true;
        }

        else if (id == R.id.favorites){

            setTitle(R.string.my_favorites_title);
            MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    if (getTitle() == getString(R.string.my_favorites_title)) {
                        setLayoutList((ArrayList<Movie>) movies);
                        Log.d(CLASS_NAME, getString(R.string.populating_viewmodel));
                    }

                }
            });

        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isConnected() {

        boolean isConnected;
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }


    private void setLayoutList(ArrayList<Movie> layoutList) {

        int spanCount;

        if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 3;
        } else {
            spanCount = 2;
        }

        RecyclerView recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        recyclerView.setHasFixedSize(true);
        RecyclerViewMovieAdapter recyclerViewMovieAdapter = new RecyclerViewMovieAdapter(getApplicationContext(), layoutList, new RecyclerViewMovieAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Movie item) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(MOVIE_EXTRA, item);
                intent.putExtra(TITLE_EXTRA, getTitle());
                intent.putExtra(ID, item.getId());
                startActivity(intent);
            }
        }

        );
        recyclerView.setAdapter(recyclerViewMovieAdapter);
        recyclerView.setClickable(true);


    }

    private void getJson(String url) {

        final RequestQueue requestQueue = RequestQue.getVolleyRequestQueueInstance(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Method.GET, url, null, new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        movies = ParseJson.parseJsonList(response);
                        setLayoutList(movies);
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(getString(R.string.volley_error), String.valueOf(error));
                    }

                });

        requestQueue.add(jsonObjectRequest);
    }

}
