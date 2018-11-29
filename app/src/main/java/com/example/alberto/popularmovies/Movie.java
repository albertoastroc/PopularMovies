package com.example.alberto.popularmovies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "movie_table")
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

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "movie_poster")
    private String moviePoster;
    @ColumnInfo (name = "movie_title")
    private String movieTitle;
    @ColumnInfo (name = "movie_overview")
    private String movieOverview;
    @ColumnInfo (name = "release_date")
    private String releaseDate;
    @ColumnInfo (name = "user_rating")
    private double userRating;

    public Movie(int id, String moviePoster, String movieTitle, String movieOverview, String releaseDate, double userRating) {
        this.id = id;
        this.moviePoster = moviePoster;
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
    }

    @Ignore
    private Movie(Parcel in) {
        id = in.readInt();
        moviePoster = in.readString();
        movieTitle = in.readString();
        movieOverview = in.readString();
        releaseDate = in.readString();
        userRating = in.readDouble();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(moviePoster);
        dest.writeString(movieTitle);
        dest.writeString(movieOverview);
        dest.writeString(releaseDate);
        dest.writeDouble(userRating);

    }
}
