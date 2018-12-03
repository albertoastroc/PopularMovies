package com.example.alberto.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.alberto.popularmovies.Movie;
import com.example.alberto.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewMovieAdapter extends RecyclerView.Adapter<RecyclerViewMovieAdapter.ViewHolder> {

    private final ArrayList<Movie> movieArrayList;
    private final LayoutInflater layoutInflater;
    private final ItemClickListener listener;

    public RecyclerViewMovieAdapter(Context context, ArrayList<Movie> movieArrayList, ItemClickListener listener) {
        this.listener = listener;
        this.layoutInflater = LayoutInflater.from(context);
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMovieAdapter.ViewHolder holder, int position) {
        holder.bind(movieArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        if(movieArrayList!= null){
            return movieArrayList.size();
        } else return 0;

    }

    public interface ItemClickListener {

        void onItemClick(Movie item);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView recyclerPosterIv;


        ViewHolder(View itemView) {
            super(itemView);
            recyclerPosterIv = itemView.findViewById(R.id.iv_main_movie_adapter);
        }

        void bind(final Movie movie, final ItemClickListener itemClickListener) {

            String posterUrl = movie.getMoviePoster();

            Picasso.get().load(posterUrl)
                    .fit()
                    .into(recyclerPosterIv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(movie);
                }
            });
        }


    }
}
