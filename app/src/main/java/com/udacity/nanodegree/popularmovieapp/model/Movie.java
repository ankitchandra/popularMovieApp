package com.udacity.nanodegree.popularmovieapp.model;

import android.net.Uri;
import android.os.Bundle;

import com.udacity.nanodegree.popularmovieapp.util.JSONUtil;

/**
 * Created by acchandr on 11/19/15.
 */
public class Movie {
    public final long id;
    public final String title;
    public final String overview;
    public final String posterPath;
    public final double voteAverage;
    public final double voteCount;
    public final String releaseDate;

    private final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public final static String EXTRA_INTENT_ARGUMENT = "com.udacity.nanodegree.movie";

    public Movie(long id, String title, String overview, String posterPath, double voteAverage, double voteCount, String releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.releaseDate = releaseDate;
    }

    public Uri getUri(String size) {
        return Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(size)
                .appendEncodedPath(posterPath)
                .build();
    }

    public Movie(Bundle bundle) {
        this(
                bundle.getLong(JSONUtil.ID),
                bundle.getString(JSONUtil.TITLE),
                bundle.getString(JSONUtil.OVERVIEW),
                bundle.getString(JSONUtil.POSTER_PATH),
                bundle.getDouble(JSONUtil.VOTER_AVERAGE),
                bundle.getLong(JSONUtil.VOTE_COUNT),
                bundle.getString(JSONUtil.RELEASE_DATE)
        );
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(JSONUtil.ID, id);
        bundle.putString(JSONUtil.TITLE, title);
        bundle.putString(JSONUtil.OVERVIEW, overview);
        bundle.putString(JSONUtil.POSTER_PATH, posterPath);
        bundle.putDouble(JSONUtil.VOTER_AVERAGE, voteAverage);
        bundle.putDouble(JSONUtil.VOTE_COUNT, voteCount);
        bundle.putString(JSONUtil.RELEASE_DATE, releaseDate);
        return bundle;
    }
}
