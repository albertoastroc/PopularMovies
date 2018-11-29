package com.example.alberto.popularmovies;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ParseJson {


    public static ArrayList<Movie> parseJson(String json) {

        JSONObject rootObject;
        ArrayList<Movie> resultsList = new ArrayList<>();

        try {

            rootObject = new JSONObject(json);
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

}


