package com.example.patelkev.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.patelkev.flicks.Adapters.MoviesRecyclerAdapter;
import com.example.patelkev.flicks.Models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements MoviesRecyclerAdapter.MovieInterface {

    ArrayList <Movie> movies;
    MoviesRecyclerAdapter moviesArrayAdapter;
    RecyclerView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialSetup();
        fetchNetworkData();
    }

    void initialSetup() {
        // initialize movies and arrayAdapter
        lvMovies = (RecyclerView) findViewById(R.id.lvMovies);
        lvMovies.setLayoutManager(new LinearLayoutManager(this));
        movies = new ArrayList<Movie>();
        moviesArrayAdapter = new MoviesRecyclerAdapter(this, movies, this);
        lvMovies.setAdapter(moviesArrayAdapter);
    }

    void fetchNetworkData() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray resultsJsonArray = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(resultsJsonArray));
                    moviesArrayAdapter.notifyDataSetChanged();
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

    @Override
    public void tappedMovieItem(Movie movie, int position) {
        Log.d("Debug", String.format("Need to show detial for %s", movie.getTitle()));
        Intent intent = new Intent(MainActivity.this,MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void showMovieTrailer(Movie movie) {
        Intent intent = new Intent(MainActivity.this, YoutubePlayerActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}
