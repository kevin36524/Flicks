package com.example.patelkev.flicks;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patelkev.flicks.Models.Movie;
import com.example.patelkev.flicks.Models.Trailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieDetailActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    TextView tvTitleView;
    TextView tvOverview;
    Movie movie;

    public static final String YT_API_KEY = "AIzaSyCZ2Vsu6rtYjeCa3k_gHQdPfqjw-qs4INY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        this.movie = (Movie) intent.getSerializableExtra("movie");

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        tvTitleView = (TextView) findViewById(R.id.tvTitleView);
        tvOverview = (TextView) findViewById(R.id.tvOverview);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        final int radius = 5;
        final int margin = 5;

        tvTitleView.setText(this.movie.getTitle());
        tvOverview.setText(this.movie.getOverview());

        this.fetchTrailerID();
    }

    private void fetchTrailerID() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(movie.getTrailerPath(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    ArrayList<Trailer> trailers = Trailer.TrailersFromArray(results);
                    fetchAndPopulateTrailer(trailers.get(0).getKey());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void fetchAndPopulateTrailer(final String videoId) {

        youTubePlayerView.initialize(YT_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(videoId);
                        // or to play immediately
                        //youTubePlayer.loadVideo("q-RBA0xoaWU");
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(MovieDetailActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
