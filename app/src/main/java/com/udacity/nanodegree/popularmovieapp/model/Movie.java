package com.udacity.nanodegree.popularmovieapp.model;

import android.net.Uri;

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
}
