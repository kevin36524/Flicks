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

import static com.example.patelkev.flicks.R.id.ivMovieImage;
import static com.example.patelkev.flicks.R.id.tvMovieOverview;

/**
 * Created by patelkev on 10/14/16.
 */

public class MoviesArrayAdapter extends ArrayAdapter<Movie> {
    public MoviesArrayAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1 , movies);
    }

    private static class ViewHolder {
        ImageView ivMovieImage;
        TextView tvMovieTitle;
        TextView tvMovieOverview;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_cell, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivMovieImage = (ImageView) convertView.findViewById(ivMovieImage);
            viewHolder.tvMovieTitle = (TextView) convertView.findViewById(R.id.tvMovieTitle);
            viewHolder.tvMovieOverview = (TextView) convertView.findViewById(tvMovieOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivMovieImage.setImageResource(0);
        viewHolder.tvMovieTitle.setText(movie.getTitle());
        viewHolder.tvMovieOverview.setText(movie.getOverview());
        Picasso.with(getContext()).load(movie.getPoster_path()).into(viewHolder.ivMovieImage);

        return convertView;
    }
}
