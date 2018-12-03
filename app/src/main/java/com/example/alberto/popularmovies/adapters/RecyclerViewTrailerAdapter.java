package com.example.alberto.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alberto.popularmovies.R;

import java.util.ArrayList;

public class RecyclerViewTrailerAdapter extends RecyclerView.Adapter<RecyclerViewTrailerAdapter.ViewHolder> {

    private final ArrayList<String> trailerArrayList;
    private final LayoutInflater layoutInflater;
    private final ItemClickListener listener;

    public RecyclerViewTrailerAdapter(Context context, ArrayList<String> trailerArrayList, ItemClickListener listener) {
        this.listener = listener;
        this.layoutInflater = LayoutInflater.from(context);
        this.trailerArrayList = trailerArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewTrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.trailer_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewTrailerAdapter.ViewHolder holder, int position) {
        holder.bind(trailerArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    public interface ItemClickListener {

        void onItemClick(String item);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView playIconTv;
        final TextView trailerNumberTv;

        ViewHolder(View itemView) {
            super(itemView);
            playIconTv = itemView.findViewById(R.id.iv_play_icon);
            trailerNumberTv = itemView.findViewById(R.id.tv_trailer_number);

        }

        void bind(final String trailer, final ItemClickListener itemClickListener) {

            playIconTv.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_play_circle_outline_red_32dp));
            trailerNumberTv.setText(String.format("Trailer %s", String.valueOf(getAdapterPosition() + 1)));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(trailer);
                }
            });
        }


    }
}
