package com.udacity.nanodegree.popularmovieapp;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;

import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.popularmovieapp.model.Movie;

import java.util.ArrayList;
import java.util.Collection;

import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by acchandr on 11/19/15.
 */
public class ImageAdapter extends BaseAdapter {
    private final ArrayList<Movie> mMovies;
    private final Context mContext;
    private final int mHeight;
    private final int mWidth;

    public ImageAdapter(Context c) {
        mContext = c;
        mMovies = new ArrayList<Movie>();
        mHeight = Math.round(mContext.getResources().getDimension(R.dimen.poster_height));
        mWidth = Math.round(mContext.getResources().getDimension(R.dimen.poster_width));
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < 0 || position >= mMovies.size())
            return null;
        return mMovies.get(position);
    }

    public void addMovies(Collection<Movie> movies){
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        Movie movie = (Movie) getItem(position);
        if (movie == null)
            return -1;
        return movie.id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);
        if (movie == null) {
            return null;
        }
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mWidth, mHeight));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView = (ImageView) convertView;
        }
        Uri uri = movie.getUri(mContext.getString(R.string.poster_size));
        Picasso.with(mContext).load(uri).into(imageView);
        return imageView;

    }
}
