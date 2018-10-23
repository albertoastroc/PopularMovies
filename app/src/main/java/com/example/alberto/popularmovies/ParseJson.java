package com.example.alberto.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ParseJson {


    public static ArrayList<Movie> parseJson(String json) {

        JSONObject rootObject;
        ArrayList<Movie> resultsList = new ArrayList<>();

        final String templateRequest = "https://image.tmdb.org/t/p/w185";

        try {

            rootObject = new JSONObject(json);
            JSONArray resultsArray = rootObject.getJSONArray("results");

            if (!(resultsArray.isNull(0))) {

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject jsonObject = resultsArray.getJSONObject(i);
                    String originalTitle = jsonObject.getString("original_title");
                    String posterPath = jsonObject.getString("poster_path");
                    String overview = jsonObject.getString("overview");
                    int userRating = jsonObject.getInt("vote_average");
                    String releaseDate = jsonObject.getString("release_date");
                    String moviePoster = templateRequest + posterPath;
                    Movie movie = new Movie(originalTitle, moviePoster, overview, userRating, releaseDate);
                    resultsList.add(movie);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultsList;

    }

}


