package com.example.alberto.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.alberto.popularmovies.Movie;

import java.util.List;

@Dao
public interface MovieDao {


    @Query("SELECT * FROM movie_table ORDER BY id")
    LiveData<List<Movie>> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie_table WHERE id = :id")
    LiveData<Movie> loadById(int id);

}
