package com.example.patelkev.flicks.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by patelkev on 10/14/16.
 */

public class Movie implements Serializable {
    String poster_path;
    String title;
    String backdrop_path;
    String overview;
    String id;
    Double vote_average;

    public String getOverview() {
        return overview;
    }

    public String getId() {
        return id;
    }

    public String getTrailerPath() {
        return  String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", id);
    }

    public String getPoster_path() {
        return  String.format("https://image.tmdb.org/t/p/w500/%s", poster_path);
    }

    public Double getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", backdrop_path);
    }

    public String getTitle() {
        return title;
    }


    public Movie(JSONObject jsonMovieData) throws JSONException {
        this.poster_path = jsonMovieData.getString("poster_path");
        this.title = jsonMovieData.getString("title");
        this.overview = jsonMovieData.getString("overview");
        this.backdrop_path = jsonMovieData.getString("backdrop_path");
        this.vote_average = jsonMovieData.getDouble("vote_average");
        this.id = jsonMovieData.getString("id");
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
