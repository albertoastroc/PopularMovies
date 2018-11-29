package com.example.alberto.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    boolean saved;
    private MovieRoomDatabase movieRoomDatabase;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("");

        movieRoomDatabase = MovieRoomDatabase.getDatabase(getApplicationContext());

        ImageView posterIv = findViewById(R.id.detail_poster_iv);
        TextView originalTitleTv = findViewById(R.id.original_title_tv);
        TextView overviewTv = findViewById(R.id.overview_tv);
        TextView userRatingTv = findViewById(R.id.user_rating_tv);
        TextView releaseDateTv = findViewById(R.id.release_date_tv);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(MainActivity.MOVIE_EXTRA);

        checkIfSaved();

        String poster = movie.getMoviePoster();
        int targetHeight = 360;
        int targetWidth = 240;
        Picasso.get()
                .load(poster)
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
                int id = movie.getId();
                Movie movie = movieRoomDatabase.movieDao().loadById(id);
                if (!(movie == null)) {
                    deleteFavorite();
                } else {
                    saveFavorite();

                }
            }
        });
    }


    private void checkIfSaved() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int id = movie.getId();
                Movie movie = movieRoomDatabase.movieDao().loadById(id);
                saved = !(movie == null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.save_favorite);
        if (saved) {
            menuItem.setIcon(R.drawable.ic_star_yellow_24dp);
        } else {
            menuItem.setIcon(R.drawable.ic_star_white_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.save_favorite) {
            saveOrDelete();
            checkIfSaved();
            if (saved) {
                item.setIcon(R.drawable.ic_star_white_24dp);
            } else {
                item.setIcon(R.drawable.ic_star_yellow_24dp);
            }

        } else if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
