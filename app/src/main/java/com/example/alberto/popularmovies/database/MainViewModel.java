package com.example.alberto.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.alberto.popularmovies.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final LiveData<List<Movie>> mlistLiveDatavies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieRoomDatabase database = MovieRoomDatabase.getDatabase(this.getApplication());
        mlistLiveDatavies = database.movieDao().getFavoriteMovies();
        Log.d("VIEWMODEL", "querying db");

    }

    public LiveData<List<Movie>> getMovies() {
        return mlistLiveDatavies;
    }

}
