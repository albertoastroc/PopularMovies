package com.example.alberto.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewMovieAdapter extends RecyclerView.Adapter<RecyclerViewMovieAdapter.ViewHolder> {

    private ArrayList<Movie> movieArrayList;
    private LayoutInflater layoutInflater;
    private ItemClickListener listener;

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
        return movieArrayList.size();
    }

    public interface ItemClickListener {

        void onItemClick(Movie item);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView recyclerPosterIv;


        public ViewHolder(View itemView) {
            super(itemView);
            recyclerPosterIv = itemView.findViewById(R.id.recycler_poster_iv);
        }

        public void bind(final Movie movie, final ItemClickListener itemClickListener) {

            String posterUrl = movie.getmMoviePoster();
            Picasso.get().load(posterUrl).resize(540, (int) (540 * 1.5)).into(recyclerPosterIv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(movie);
                }
            });
        }


    }
}
