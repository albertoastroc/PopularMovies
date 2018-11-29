package com.example.alberto.popularmovies;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;



@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = MovieRoomDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movie_database";
    private static MovieRoomDatabase sInstance;

    public static MovieRoomDatabase getDatabase(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "ceating db instance");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class,
                            MovieRoomDatabase.DATABASE_NAME)
                            .build();
            }
        }

        Log.d(LOG_TAG, "Getting database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();

}
