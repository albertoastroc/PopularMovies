package com.example.alberto.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson {



    public static ArrayList<Movie> parseJson(String json){

        JSONObject rootObject;
        ArrayList<Movie> resultsList = new ArrayList<>();

        final String templateRequest = "https://image.tmdb.org/t/p/w185";

        try {

            rootObject = new JSONObject(json);
            JSONArray resultsArray = rootObject.getJSONArray("results");

            if (!(resultsArray.isNull(0))){

                for (int i = 0; i < resultsArray.length(); i++){
                    JSONObject jsonObject = resultsArray.getJSONObject(i);
                    String posterPath = jsonObject.getString("poster_path");
                    String moviePoster = templateRequest + posterPath;
                    Movie movie = new Movie(moviePoster);
                    resultsList.add(movie);
                }

            }


            } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultsList;

    }

    }


