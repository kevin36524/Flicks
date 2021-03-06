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

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by patelkev on 10/15/16.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mcontext;
    ArrayList<Movie> movies;
    MovieInterface tapDelegate;

    private final int PORTRAIT = 0, LANDSCAPE = 1, FILL = 2;

    public MoviesRecyclerAdapter(Context context, ArrayList<Movie> movies, MovieInterface tapDelegate) {
        this.mcontext = context;
        this.movies = movies;
        this.tapDelegate = tapDelegate;
    }

    public static interface MovieInterface {
        public void tappedMovieItem(Movie movie, int position);
        public void showMovieTrailer(Movie movie);
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
        View movieCell;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == PORTRAIT) {
            movieCell = inflater.inflate(R.layout.movie_cell, parent, false);
            viewHolder = new ViewHolder(movieCell, this);
        } else if (viewType == FILL) {
            movieCell = inflater.inflate(R.layout.movie_cell_full, parent, false);
            viewHolder = new ViewHolderFill(movieCell, this, parent);
        } else {
            movieCell = inflater.inflate(R.layout.movie_cell, parent, false);
            viewHolder = new ViewHolderLand(movieCell, this);
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
            Picasso.with(this.mcontext).load(movie.getPoster_path()).transform(new RoundedCornersTransformation(30,30)).into(vh.ivMovieImage);
        } else if (viewType == FILL) {
            ViewHolderFill vh = (ViewHolderFill) holder;
            vh.ivMovieImage.setImageResource(0);
            Picasso.with(this.mcontext).load(movie.getBackdrop_path()).resize(vh.parentWidth,0).transform(new RoundedCornersTransformation(30,30)).into(vh.ivMovieImage);
        } else {
            ViewHolderLand vh = (ViewHolderLand) holder;

            vh.tvMovieOverview.setText(movie.getOverview());
            vh.tvMovieTitle.setText(movie.getTitle());
            vh.ivMovieImage.setImageResource(0);
            Picasso.with(this.mcontext).load(movie.getBackdrop_path()).transform(new RoundedCornersTransformation(30, 30)).into(vh.ivMovieImage);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class  ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivMovieImage;
        TextView tvMovieTitle;
        TextView tvMovieOverview;
        MoviesRecyclerAdapter adapter;

        public ViewHolder(View itemView, MoviesRecyclerAdapter adapter) {
            super(itemView);

            this.adapter = adapter;
            itemView.setOnClickListener(this);
            ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            tvMovieOverview = (TextView) itemView.findViewById(R.id.tvMovieOverview);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = this.adapter.movies.get(position);

            this.adapter.tapDelegate.tappedMovieItem(movie,position);
        }
    }

    public static class  ViewHolderLand extends  RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivMovieImage;
        TextView tvMovieTitle;
        TextView tvMovieOverview;
        MoviesRecyclerAdapter adapter;

        public ViewHolderLand(View itemView, MoviesRecyclerAdapter adapter) {
            super(itemView);

            this.adapter = adapter;
            itemView.setOnClickListener(this);
            ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            tvMovieOverview = (TextView) itemView.findViewById(R.id.tvMovieOverview);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = this.adapter.movies.get(position);

            this.adapter.tapDelegate.tappedMovieItem(movie, position);
        }
    }

    public static class ViewHolderFill extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivMovieImage;
        ImageView ivPlayImage;
        MoviesRecyclerAdapter adapter;
        int parentWidth;

        public ViewHolderFill(View itemView, MoviesRecyclerAdapter adapter, ViewGroup parent) {
            super(itemView);

            itemView.setOnClickListener(this);
            this.parentWidth = parent.getWidth();
            this.adapter = adapter;
            this.ivPlayImage = (ImageView) itemView.findViewById(R.id.ivPlayImage);
            this.ivMovieImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = this.adapter.movies.get(position);

            this.adapter.tapDelegate.showMovieTrailer(movie);
        }
    }
}
