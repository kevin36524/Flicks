package com.example.patelkev.flicks.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by patelkev on 10/16/16.
 */

public class Trailer {
    String name;
    String key;

    public String getKey() {
        return key;
    }

    public Trailer(JSONObject trailer) throws JSONException {
        this.name = trailer.getString("name");
        this.key = trailer.getString("key");
    }

    public static ArrayList<Trailer> TrailersFromArray(JSONArray trailers) {
        ArrayList<Trailer> returnTrailers = new ArrayList<Trailer>();

        for (int i = 0; i < trailers.length(); i++) {
            try {
                returnTrailers.add(new Trailer(trailers.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return returnTrailers;
    }
}
