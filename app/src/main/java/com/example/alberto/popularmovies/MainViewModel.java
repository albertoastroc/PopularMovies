package com.example.alberto.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieRoomDatabase database = MovieRoomDatabase.getDatabase(this.getApplication());
        movies = database.movieDao().getFavoriteMovies();
        Log.d("VIEWMODEL", "querying db");

    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
