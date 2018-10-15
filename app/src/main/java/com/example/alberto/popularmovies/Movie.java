package com.example.alberto.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

    String mMoviePoster;
    String mMovieTitle;
    String mMovieOverview;
    String mReleaseDate;
    int mUserRating;

    public Movie(String mMoviePoster) {
        this.mMoviePoster = mMoviePoster;
    }


    protected Movie(Parcel in) {
        mMoviePoster = in.readString();
        mMovieTitle = in.readString();
        mMovieOverview = in.readString();
        mReleaseDate = in.readString();
        mUserRating = in.readInt();
    }

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

    public int getmUserRating() {
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
        dest.writeInt(mUserRating);
    }
}
