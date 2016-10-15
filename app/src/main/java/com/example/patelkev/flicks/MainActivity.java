package com.example.patelkev.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.patelkev.flicks.Adapters.MoviesArrayAdapter;
import com.example.patelkev.flicks.Models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ArrayList <Movie> movies;
    MoviesArrayAdapter moviesArrayAdapter;
    ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialSetup();
        fetchNetworkData();
    }

    void initialSetup() {
        // initialize movies and arrayAdapter
        lvMovies = (ListView) findViewById(R.id.lvMovies);

        movies = new ArrayList<Movie>();
        moviesArrayAdapter = new MoviesArrayAdapter(this, movies);

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
}
