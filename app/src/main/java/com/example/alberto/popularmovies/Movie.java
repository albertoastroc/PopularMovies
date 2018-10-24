package com.example.alberto.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

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
    private String mMoviePoster;
    private String mMovieTitle;
    private String mMovieOverview;
    private String mReleaseDate;
    private double mUserRating;

    Movie(String originalTitle, String moviePoster, String overview, double userRating, String releaseDate) {
        this.mMoviePoster = moviePoster;
        this.mMovieTitle = originalTitle;
        this.mMovieOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mUserRating = userRating;
    }

    private Movie(Parcel in) {
        mMoviePoster = in.readString();
        mMovieTitle = in.readString();
        mMovieOverview = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readDouble();
    }

    public String getmMoviePoster() {
        return mMoviePoster;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public String getmMovieOverview() {
        return mMovieOverview;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public double getmUserRating() {
        return mUserRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMoviePoster);
        dest.writeString(mMovieTitle);
        dest.writeString(mMovieOverview);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mUserRating);
    }
}
