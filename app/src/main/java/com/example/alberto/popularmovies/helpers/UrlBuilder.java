package com.example.alberto.popularmovies.helpers;

import android.net.Uri;

public class UrlBuilder {

    private static final String API_KEY = "";


    public static String listBuilder(String preference) {


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(preference)
                .appendQueryParameter("page", "1")
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("api_key", API_KEY);

        return builder.toString();

    }

    public static String trailerOrReviewBuilder(String movieId, String preference) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieId)
                .appendPath(preference)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("api_key", API_KEY);

        return builder.toString();


    }

    public static String youtubeBuilder(String trailerId){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.youtube.com")
                .appendPath("watch")
                .appendQueryParameter("v", trailerId);

        return builder.toString();
    }

}
