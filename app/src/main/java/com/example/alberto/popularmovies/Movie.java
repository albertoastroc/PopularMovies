package com.example.alberto.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "movie_table")
public class Movie implements Parcelable {

    @androidx.annotation.NonNull
    @ColumnInfo
    private Movie movie;

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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

    @PrimaryKey
    private int id;
    @ColumnInfo (name = "movie_poster")
    private final String moviePoster;
    @ColumnInfo (name = "movie_title")
    private final String movieTitle;
    @ColumnInfo (name = "movie_overview")
    private final String movieOverview;
    @ColumnInfo (name = "release_date")
    private final String releaseDate;
    @ColumnInfo (name = "user_rating")
    private final double userRating;

    Movie(String originalTitle, String moviePoster, String overview, double userRating, String releaseDate, int id) {
        this.moviePoster = moviePoster;
        this.movieTitle = originalTitle;
        this.movieOverview = overview;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.id = id;
    }

    private Movie(Parcel in) {
        moviePoster = in.readString();
        movieTitle = in.readString();
        movieOverview = in.readString();
        releaseDate = in.readString();
        userRating = in.readDouble();
        id = in.readInt();
    }

    public void setMoviePoster(String moviePoster){
        this.moviePoster = moviePoster;
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
        dest.writeInt(id);
    }
}
