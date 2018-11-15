package com.example.alberto.popularmovies;

import android.arch.lifecycle.LiveData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDao {


    @Query("SELECT * FROM movie_table ORDER by movie asc")
    LiveData<List<Movie>> getFavoriteMovies();

    // on conflic strategy?
    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);





}
