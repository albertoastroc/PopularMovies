package com.example.alberto.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.alberto.popularmovies.adapters.RecyclerViewReviewAdapter;
import com.example.alberto.popularmovies.adapters.RecyclerViewTrailerAdapter;
import com.example.alberto.popularmovies.database.MovieRoomDatabase;
import com.example.alberto.popularmovies.helpers.AppExecutors;
import com.example.alberto.popularmovies.helpers.ParseJson;
import com.example.alberto.popularmovies.helpers.RequestQue;
import com.example.alberto.popularmovies.helpers.UrlBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID_FOR_BUNDLE = "movie_id";

    private boolean saved;
    private MovieRoomDatabase movieRoomDatabase;
    private Movie movie;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(MainActivity.MOVIE_EXTRA);

        if (savedInstanceState != null){
            id = savedInstanceState.getInt(MOVIE_ID_FOR_BUNDLE);
            getTrailers(getTrailerUrl(id));
            getReviews(getReviewUrl(id));

        } else {
            id = intent.getIntExtra(MainActivity.ID, 0);
        }


        movieRoomDatabase = MovieRoomDatabase.getDatabase(getApplicationContext());

        setTitle("");

        ImageView posterIv = findViewById(R.id.iv_detail_poster);
        TextView originalTitleTv = findViewById(R.id.tv_original_title);
        TextView overviewTv = findViewById(R.id.tv_overview);
        TextView userRatingTv = findViewById(R.id.tv_user_rating);
        TextView releaseDateTv = findViewById(R.id.tv_release_date);

        String poster = movie.getMoviePoster();
        int targetHeight = 360;
        int targetWidth = 240;
        Picasso.get()
                .load(poster)
                .resize(targetWidth, targetHeight)
                .into(posterIv);


        String trailerUrlString = getTrailerUrl(id);
        getTrailers(trailerUrlString);

        String reviewUrlString = getReviewUrl(id);
        getReviews(reviewUrlString);

        String originalTitle = movie.getMovieTitle();
        String overview = movie.getMovieOverview();
        double userRating = movie.getUserRating();
        String releaseDate = movie.getReleaseDate();

        originalTitleTv.setText(originalTitle);
        overviewTv.setText(overview);
        userRatingTv.setText(String.format("%s/10", String.valueOf(userRating)));
        String substring = releaseDate.substring(0, 4);
        releaseDateTv.setText(substring);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MOVIE_ID_FOR_BUNDLE, id);
        super.onSaveInstanceState(outState);
    }

    private void setReviewList(ArrayList<Review> layoutList) {

        RecyclerView recyclerView = findViewById(R.id.rv_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(true);
        RecyclerViewReviewAdapter recyclerViewReviewAdapter = new RecyclerViewReviewAdapter(getApplicationContext(), layoutList);

        recyclerView.setAdapter(recyclerViewReviewAdapter);
        recyclerView.setClickable(true);


    }

    private void setTrailerList(ArrayList<String> layoutList) {

        RecyclerView recyclerView = findViewById(R.id.rv_trailers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(true);
        RecyclerViewTrailerAdapter recyclerViewReviewAdapter = new RecyclerViewTrailerAdapter(getApplicationContext(), layoutList, new RecyclerViewTrailerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlBuilder.youtubeBuilder(item)));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(recyclerViewReviewAdapter);
        recyclerView.setClickable(true);


    }

    private void getReviews(final String url) {

        final RequestQueue requestQueue = RequestQue.getVolleyRequestQueueInstance(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Review> reviews;
                        reviews = ParseJson.parseJsonReview(response);
                        int reviewCount = reviews.size();

                        for (int i = 0; i < reviewCount; i++) {

                            setReviewList(reviews);

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error", error.toString());
                    }

                });

        requestQueue.add(jsonObjectRequest);

    }

    private void getTrailers(final String url) {

        final RequestQueue requestQueue = RequestQue.getVolleyRequestQueueInstance(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<String> trailerIds;
                        trailerIds = ParseJson.parseJsonTrailer(response);
                        int trailerCount = trailerIds.size();

                        for (int i = 0; i < trailerCount; i++) {

                            setTrailerList(trailerIds);

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(getString(R.string.volley_error), error.toString());
                    }

                });

        requestQueue.add(jsonObjectRequest);
    }

    private void saveFavorite() {
        movieRoomDatabase.movieDao().insert(movie);
    }

    private void deleteFavorite() {
        movieRoomDatabase.movieDao().delete(movie);

    }

    private void saveOrDelete() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (saved) {
                    deleteFavorite();
                    saved = false;
                } else {
                    saveFavorite();
                    saved = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        super.onPrepareOptionsMenu(menu);

        final LiveData<Movie> movieFromDb = movieRoomDatabase.movieDao()
                .loadById(movie.getId());
        movieFromDb.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                MenuItem favoritesIcon = menu.getItem(0);
                if (movie == null) {
                    saved = false;
                    favoritesIcon.setIcon(R.drawable.ic_star_white_24dp);
                } else {
                    favoritesIcon.setIcon(R.drawable.ic_star_yellow_24dp);
                    saved = true;
                }
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.save_favorite) {
            saveOrDelete();
            if (saved) {
                item.setIcon(R.drawable.ic_star_white_24dp);
            } else {
                item.setIcon(R.drawable.ic_star_yellow_24dp);
            }

        } else if (id == android.R.id.home) {
            Intent intent = getIntent();
            intent.getStringExtra(MainActivity.TITLE_EXTRA);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private String getTrailerUrl(int id) {

        String stringId = String.valueOf(id);
        return UrlBuilder.trailerOrReviewBuilder(stringId, getString(R.string.videos));

    }

    private String getReviewUrl(int id) {

        String stringId = String.valueOf(id);
        return UrlBuilder.trailerOrReviewBuilder(stringId, getString(R.string.reviews));
    }

}
