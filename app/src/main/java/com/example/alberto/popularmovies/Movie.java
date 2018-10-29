package com.example.alberto.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private final String moviePoster;
    private final String movieTitle;
    private final String movieOverview;
    private final String releaseDate;
    private final double userRating;

    Movie(String originalTitle, String moviePoster, String overview, double userRating, String releaseDate) {
        this.moviePoster = moviePoster;
        this.movieTitle = originalTitle;
        this.movieOverview = overview;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
    }

    private Movie(Parcel in) {
        moviePoster = in.readString();
        movieTitle = in.readString();
        movieOverview = in.readString();
        releaseDate = in.readString();
        userRating = in.readDouble();
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getUserRating() {
        return userRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moviePoster);
        dest.writeString(movieTitle);
        dest.writeString(movieOverview);
        dest.writeString(releaseDate);
        dest.writeDouble(userRating);
    }
}
