package com.example.patelkev.flicks.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patelkev.flicks.Models.Movie;
import com.example.patelkev.flicks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by patelkev on 10/14/16.
 */

public class MoviesArrayAdapter extends ArrayAdapter<Movie> {
    public MoviesArrayAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1 , movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item
        Movie movie = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_cell, parent, false);
        }

        ImageView ivMovieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        TextView tvMovieTitle = (TextView) convertView.findViewById(R.id.tvMovieTitle);
        TextView tvMovieOverview = (TextView) convertView.findViewById(R.id.tvMovieOverview);

        ivMovieImage.setImageResource(0);
        tvMovieTitle.setText(movie.getTitle());
        tvMovieOverview.setText(movie.getOverview());
        Picasso.with(getContext()).load(movie.getPoster_path()).into(ivMovieImage);

        return convertView;
    }
}
