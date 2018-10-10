package com.example.alberto.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private final String API_KEY = "aa7c95df42b9e34788ea40bcfb3d83c9";

    ArrayList<Movie> mMoviesList = new ArrayList<>();
    Context mContext;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);

        mMoviesList = objects;
        mContext = context;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

            Movie movie = mMoviesList.get(position);

            TextView textView = listItem.findViewById(R.id.listTextView);
            textView.setText(movie.getmMovieName());
        }

        return listItem;
    }

    @Override
    public int getCount() {
        return mMoviesList.size();
    }
}
