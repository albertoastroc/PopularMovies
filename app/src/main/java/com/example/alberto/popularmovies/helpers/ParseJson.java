package com.example.alberto.popularmovies.helpers;

import android.net.Uri;

import com.example.alberto.popularmovies.Movie;
import com.example.alberto.popularmovies.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson {


    public static ArrayList<Movie> parseJsonList(JSONObject json) {

        JSONObject rootObject;
        ArrayList<Movie> resultsList = new ArrayList<>();

        try {

            rootObject = json;
            JSONArray resultsArray = rootObject.getJSONArray("results");

            if (!(resultsArray.isNull(0))) {

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject jsonObject = resultsArray.getJSONObject(i);

                    String originalTitle = jsonObject.getString("original_title");
                    String posterPath = jsonObject.getString("poster_path");
                    String overview = jsonObject.getString("overview");
                    double userRating = jsonObject.getDouble("vote_average");
                    String releaseDate = jsonObject.getString("release_date");
                    int id = jsonObject.getInt("id");

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https")
                            .authority("image.tmdb.org")
                            .appendPath("t")
                            .appendPath("p")
                            .appendPath("w342")
                            .appendEncodedPath(posterPath);

                    String posterUrl = builder.build().toString();

                    Movie movie = new Movie(id, posterUrl, originalTitle, overview, releaseDate, userRating);
                    resultsList.add(movie);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultsList;

    }

    public static ArrayList<String> parseJsonTrailer(JSONObject jsonObject) {

        JSONObject rootObject;
        ArrayList<String> trailerIds = new ArrayList<>();


        try {

            rootObject = jsonObject;
            JSONArray trailersArray = rootObject.getJSONArray("results");

            if (!(trailersArray.isNull(0))) {

                for (int i = 0; i < trailersArray.length(); i++) {
                    JSONObject jsonObjectTrailer = trailersArray.getJSONObject(i);

                    String trailerId;
                    String type = jsonObjectTrailer.getString("type");

                    if (type.equals("Trailer")) {

                        trailerId = jsonObjectTrailer.getString("key");
                        trailerIds.add(trailerId);
                    }


                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerIds;
    }

    public static ArrayList<Review> parseJsonReview(JSONObject jsonObject) {

        JSONObject rootObject;
        ArrayList<Review> reviews = new ArrayList<>();


        try {

            rootObject = jsonObject;
            JSONArray trailersArray = rootObject.getJSONArray("results");

            if (!(trailersArray.isNull(0))) {

                for (int i = 0; i < trailersArray.length(); i++) {
                    JSONObject jsonObjectReviews = trailersArray.getJSONObject(i);

                    String author = jsonObjectReviews.getString("author");
                    String content = jsonObjectReviews.getString("content");
                    Review review = new Review(author, content);

                    reviews.add(review);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }


}


