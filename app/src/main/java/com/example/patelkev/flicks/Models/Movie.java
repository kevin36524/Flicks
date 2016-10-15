package com.example.patelkev.flicks.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by patelkev on 10/14/16.
 */

public class Movie {
    String poster_path;
    String title;

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return  String.format("https://image.tmdb.org/t/p/w500/%s", poster_path);
    }

    public String getTitle() {
        return title;
    }

    String overview;

    public Movie(JSONObject jsonMovieData) throws JSONException {
        this.poster_path = jsonMovieData.getString("poster_path");
        this.title = jsonMovieData.getString("title");
        this.overview = jsonMovieData.getString("overview");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonMovie = null;
            try {
                jsonMovie = jsonArray.getJSONObject(i);
                movieList.add(new Movie(jsonMovie));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movieList;
    }
}
