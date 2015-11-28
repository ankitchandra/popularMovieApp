package com.udacity.nanodegree.popularmovieapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.nanodegree.popularmovieapp.R;
import com.udacity.nanodegree.popularmovieapp.model.Movie;
import com.udacity.nanodegree.popularmovieapp.util.JSONUtil;

/**
 * Created by acchandr on 11/23/15.
 */
public class MovieDetailActivity extends AppCompatActivity {

    //    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Movie.EXTRA_INTENT_ARGUMENT)) {
            Movie movie = new Movie(intent.getBundleExtra(Movie.EXTRA_INTENT_ARGUMENT));
            ((TextView) findViewById(R.id.movie_title)).setText(movie.title);
            ((TextView) findViewById(R.id.movie_rating)).setText(movie.voteAverage+"/10");
            ((TextView) findViewById(R.id.movie_overview)).setText(movie.overview);
            ((TextView) findViewById(R.id.movie_release_date)).setText(movie.releaseDate);


            Uri posterUri = movie.getUri(getString(R.string.poster_size));
            Picasso.with(this)
                    .load(posterUri)
                    .into((ImageView) findViewById(R.id.movie_poster));
        }
    }
}
