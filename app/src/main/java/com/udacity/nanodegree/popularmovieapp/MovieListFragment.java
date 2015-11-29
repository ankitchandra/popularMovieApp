package com.udacity.nanodegree.popularmovieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.nanodegree.popularmovieapp.activity.MovieDetailActivity;
import com.udacity.nanodegree.popularmovieapp.model.Movie;
import com.udacity.nanodegree.popularmovieapp.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {

    private ImageAdapter mImageAdapter;
    private int mPagesLoaded = 0;
    public static int MAX_PAGES = 1000;
    private boolean mIsLoading = false;

    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mImageAdapter = new ImageAdapter(getActivity());
        GridView gridView = (GridView) view.findViewById(R.id.gridview_movies);
        if (gridView == null) {
            return null;
        }
        gridView.setAdapter(mImageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v,
                                    int position,
                                    long id) {

                ImageAdapter adapter = (ImageAdapter) parent.getAdapter();
                Movie movie = (Movie) adapter.getItem(position);

                if (movie == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Movie.EXTRA_INTENT_ARGUMENT, movie.toBundle());
                getActivity().startActivity(intent);
            }
        });
        gridView.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        int last = firstVisibleItem + visibleItemCount;
                        if (last == totalItemCount) {
                            loadMovies();
                        }
                    }
                }
        );

        loadMovies();

        return view;
    }

    private void loadMovies() {
        if (mIsLoading) {
            return;
        }
        if (mPagesLoaded >= MAX_PAGES) {
            return;
        }
        mIsLoading = true;
        new FetchMoviesTask().execute(mPagesLoaded + 1);
    }

    private void pauseLoadingMovies() {
        if (!mIsLoading) {
            return;
        }
        mIsLoading = false;
    }

    private class FetchMoviesTask extends AsyncTask<Integer, Void, Collection<Movie>> {
        public final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        private final String BASE_URL = "http://api.themoviedb.org/3/movie";
        private final String PARAM_PAGE = "page";
        private final String API_KEY = "api_key";

        @Override
        protected Collection<Movie> doInBackground(Integer... params) {
            if (params.length == 0) {
                return null;
            }
            int page = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Collection<Movie> movies = new ArrayList<Movie>();
            final String API_SORTING = PreferenceManager
                    .getDefaultSharedPreferences(getActivity())
                    .getString(
                            getString(R.string.pref_sorting_key),
                            getString(R.string.pref_default_value)
                    );
            final String API_KEY_VALUE = getString(R.string.api_key);

            try {
                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(API_SORTING)
                        .appendQueryParameter(API_KEY, API_KEY_VALUE)
                        .appendQueryParameter(PARAM_PAGE, String.valueOf(page))
                        .build();
                Log.i(LOG_TAG, "Uri built " + uri.toString());
                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                if (buffer.length() == 0) {
                    return movies;
                }
                movies.addAll(fetchMovies(buffer.toString()));
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception encountered " + e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Exception encountered while closing reader");
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return movies;
        }

        private Collection<Movie> fetchMovies(String jsonResponse) throws JSONException {
            JSONObject object = new JSONObject(jsonResponse);
            JSONArray array = object.getJSONArray("results");
            ArrayList<Movie> result = new ArrayList<Movie>();
            for (int i = 0; i < array.length(); i++) {
                result.add(JSONUtil.getMovie(array.getJSONObject(i)));
            }
            return result;
        }

        @Override
        protected void onPostExecute(Collection<Movie> movies) {
            if (movies == null) {
                pauseLoadingMovies();
                return;
            }
            mPagesLoaded++;
            pauseLoadingMovies();
            mImageAdapter.addMovies(movies);
        }
    }
}
