package com.example.alberto.popularmovies;

public class Movie {

    String mMovieName;
    String mMovieYear;

    public Movie(String mMovieName) {
        this.mMovieName = mMovieName;
    }

    public String getmMovieName() {
        return mMovieName;
    }

    public void setmMovieName(String mMovieName) {
        this.mMovieName = mMovieName;
    }

    public String getmMovieYear() {
        return mMovieYear;
    }

    public void setmMovieYear(String mMovieYear) {
        this.mMovieYear = mMovieYear;
    }
}
