package com.udacity.nanodegree.popularmovieapp.util;

import com.udacity.nanodegree.popularmovieapp.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by acchandr on 11/20/15.
 */
public class JSONUtil {
    public final static String ID = "id";
    public static final String TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String POSTER_PATH = "poster_path";
    public static final String VOTER_AVERAGE = "vote_average";
    public static final String VOTE_COUNT = "vote_count";
    public static final String RELEASE_DATE = "release_date";

    public final static Movie getMovie(JSONObject movieJson) throws JSONException{
        return new Movie(
                movieJson.getLong(ID),
                movieJson.getString(TITLE),
                movieJson.getString(OVERVIEW),
                movieJson.getString(POSTER_PATH),
                movieJson.getDouble(VOTER_AVERAGE),
                movieJson.getDouble(VOTE_COUNT),
                movieJson.getString(RELEASE_DATE)
        );
    }
}
