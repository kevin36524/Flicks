package com.example.patelkev.flicks;

import android.content.Intent;
import android.os.Bundle;
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

public class YoutubePlayerActivity extends YouTubeBaseActivity {

    public Movie movie;
    YouTubePlayerView youTubePlayerView;
    public static final String YT_API_KEY = "AIzaSyCZ2Vsu6rtYjeCa3k_gHQdPfqjw-qs4INY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        Intent intent = getIntent();
        this.movie = (Movie) intent.getSerializableExtra("movie");

        youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        fetchTrailerID();
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
                        youTubePlayer.loadVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(YoutubePlayerActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}