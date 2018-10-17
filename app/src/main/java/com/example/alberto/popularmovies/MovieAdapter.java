package com.example.alberto.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    ArrayList<Movie> mMoviesList;
    Context mContext;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);

        mMoviesList = objects;
        mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);
        }

        Movie movie = getItem(position);
        String url = movie.getmMoviePoster();

        GridView gridView = (GridView) parent;
        int columnWidth = gridView.getColumnWidth();
        Picasso.get().load(url).resize(columnWidth, (int) (columnWidth * 1.5)).into(view);

        return view;

    }

    @Override
    public int getCount() {
        return mMoviesList.size();
    }
}
