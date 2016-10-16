package com.example.patelkev.flicks.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patelkev.flicks.Models.Movie;
import com.example.patelkev.flicks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by patelkev on 10/15/16.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mcontext;
    ArrayList<Movie> movies;

    private final int PORTRAIT = 0, LANDSCAPE = 1, FILL = 2;

    public MoviesRecyclerAdapter(Context context, ArrayList<Movie> movies) {
        this.mcontext = context;
        this.movies = movies;
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = this.movies.get(position);

        if(movie.getVote_average() > 5) {
            return FILL;
        }

        int orientation = this.mcontext.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return LANDSCAPE;
        }

        return PORTRAIT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieCell = inflater.inflate(R.layout.movie_cell, parent, false);
        RecyclerView.ViewHolder viewHolder;
        if (viewType == PORTRAIT) {
            viewHolder = new ViewHolder(movieCell);
        } else if (viewType == FILL) {
            viewHolder = new ViewHolderFill(movieCell);
        }else {
            viewHolder = new ViewHolderLand(movieCell);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        int viewType = holder.getItemViewType();

        if (viewType == PORTRAIT) {
            ViewHolder vh = (ViewHolder) holder;

            vh.tvMovieOverview.setText(movie.getOverview());
            vh.tvMovieTitle.setText(movie.getTitle());
            vh.ivMovieImage.setImageResource(0);
            Picasso.with(this.mcontext).load(movie.getPoster_path()).into(vh.ivMovieImage);
        } else if (viewType == FILL) {
            ViewHolderFill vh = (ViewHolderFill) holder;
            vh.ivMovieImage.setImageResource(0);
            Picasso.with(this.mcontext).load(movie.getBackdrop_path()).into(vh.ivMovieImage);
        }else {
            ViewHolderLand vh = (ViewHolderLand) holder;

            vh.tvMovieOverview.setText(movie.getOverview());
            vh.tvMovieTitle.setText(movie.getTitle());
            vh.ivMovieImage.setImageResource(0);
            Picasso.with(this.mcontext).load(movie.getBackdrop_path()).into(vh.ivMovieImage);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class  ViewHolder extends  RecyclerView.ViewHolder {
        ImageView ivMovieImage;
        TextView tvMovieTitle;
        TextView tvMovieOverview;

        public ViewHolder(View itemView) {
            super(itemView);

            ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            tvMovieOverview = (TextView) itemView.findViewById(R.id.tvMovieOverview);
        }
    }

    public static class  ViewHolderLand extends  RecyclerView.ViewHolder {
        ImageView ivMovieImage;
        TextView tvMovieTitle;
        TextView tvMovieOverview;

        public ViewHolderLand(View itemView) {
            super(itemView);

            ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            tvMovieOverview = (TextView) itemView.findViewById(R.id.tvMovieOverview);
        }
    }

    public static class ViewHolderFill extends RecyclerView.ViewHolder {
        ImageView ivMovieImage;

        public ViewHolderFill(View itemView) {
            super(itemView);
            this.ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
        }
    }
}
