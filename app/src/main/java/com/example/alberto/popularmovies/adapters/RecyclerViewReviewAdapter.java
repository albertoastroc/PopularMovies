package com.example.alberto.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alberto.popularmovies.R;
import com.example.alberto.popularmovies.Review;

import java.util.ArrayList;

public class RecyclerViewReviewAdapter extends RecyclerView.Adapter<RecyclerViewReviewAdapter.ViewHolder> {

    private final ArrayList<Review> reviewArrayList;
    private final LayoutInflater layoutInflater;

    public RecyclerViewReviewAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.review_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewReviewAdapter.ViewHolder holder, int position) {
        holder.bind(reviewArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView contentTextView;
        final TextView authorTextView;


        ViewHolder(View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.tv_content);
            authorTextView = itemView.findViewById(R.id.tv_author);
        }

        void bind(final Review review) {

            String contentString = review.getContent();
            String authorString = review.getAuthor();

            contentTextView.setText(contentString);
            authorTextView.setText(String.format("by %s", authorString));
        }


    }
}
