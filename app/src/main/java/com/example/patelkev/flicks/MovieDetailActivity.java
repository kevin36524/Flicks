package com.example.patelkev.flicks;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patelkev.flicks.Models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    ImageView ivMoviePosterImage;
    TextView tvTitleView;
    TextView tvOverview;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        this.movie = (Movie) intent.getSerializableExtra("movie");

        ivMoviePosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitleView = (TextView) findViewById(R.id.tvTitleView);
        tvOverview = (TextView) findViewById(R.id.tvOverview);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        Picasso.with(this).load(this.movie.getBackdrop_path()).resize(width, 0).into(ivMoviePosterImage);
        tvTitleView.setText(this.movie.getTitle());
        tvOverview.setText(this.movie.getOverview());
    }
}
