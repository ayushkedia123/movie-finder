package com.ayush.moviefinder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ayush.moviefinder.R;
import com.ayush.moviefinder.adapter.MovieListAdapter;
import com.ayush.moviefinder.model.ErrorObject;
import com.ayush.moviefinder.model.Movie;
import com.ayush.moviefinder.model.MovieSearchList;
import com.ayush.moviefinder.network.ClientGenerator;
import com.ayush.moviefinder.network.MFResponseListener;
import com.ayush.moviefinder.network.RequestBuilder;
import com.ayush.moviefinder.utils.AppUtil;
import com.ayush.moviefinder.view.AppConstant;
import com.ayush.moviefinder.view.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashSet;

public class MoviesListActivity extends AppCompatActivity implements View.OnClickListener, OnLoadMoreListener {

    private ListView movieListView;
    private String searchText;
    private String yearText;
    private String movieType;
    private ProgressBar progressBar;
    private MovieListAdapter movieListAdapter;
    private ArrayList<Movie> moviesList;
    private int currentPage = 1;
    private View footerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        movieListView = (ListView) findViewById(R.id.lv_movies_list);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        searchText = getIntent().getStringExtra(AppConstant.intentExtras.SEARCH_TEXT);
        yearText = getIntent().getStringExtra(AppConstant.intentExtras.YEAR_TEXT);
        movieType = getIntent().getStringExtra(AppConstant.intentExtras.MOVIE_TYPE);
        callMoviesListApi(currentPage, false);
        initListeners();
    }

    /* Initialising listeners */

    private void initListeners() {
        footerView = LayoutInflater.from(this).inflate(R.layout.progress_bar, null);
        movieListView.addFooterView(footerView);
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MoviesListActivity.this, MovieDetailActivity.class);
                i.putExtra(AppConstant.intentExtras.IMDB_ID, moviesList.get(position).imdbID);
                i.putExtra(AppConstant.intentExtras.IMAGE_URL, moviesList.get(position).Poster);
                MoviesListActivity.this.startActivity(i);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }

    /* update method to hide and show the loader  */

    public void updateFooterVisibility(boolean visibility) {
        if (visibility) {
            footerView.setVisibility(View.VISIBLE);
        } else {
            footerView.setVisibility(View.GONE);
        }
    }

    /* calling api to get movies list based on the search text, year and type */

    private void callMoviesListApi(final int currentPage, final boolean isLoadMore) {
        if (!isLoadMore)
            progressBar.setVisibility(View.VISIBLE);
        RequestBuilder requestBuilder = ClientGenerator.createService(RequestBuilder.class);
        requestBuilder.getMovieList(searchText, yearText, movieType, currentPage + "", new MFResponseListener<MovieSearchList>(this) {
            @Override
            public void onSuccess(MovieSearchList result) {
                progressBar.setVisibility(View.GONE);
                if (result != null)
                    if (result.Response != null && !result.Response.equalsIgnoreCase("False")) {
                        if (movieListAdapter == null) {
                            moviesList = result.Search;
                            movieListAdapter = new MovieListAdapter(MoviesListActivity.this, moviesList);
                            movieListAdapter.setPage(currentPage * 10);
                            movieListAdapter.setOnLoadMoreListener(MoviesListActivity.this);
                            movieListView.setAdapter(movieListAdapter);
                        } else {
                            moviesList.addAll(result.Search);
                            movieListAdapter.setPage(currentPage * 10);
                            movieListAdapter.notifyDataSetChanged();
                        }
                    } else
                        AppUtil.showShortToast(MoviesListActivity.this, result.Error);

            }

            @Override
            public void onError(ErrorObject error) {
                progressBar.setVisibility(View.GONE);
                AppUtil.showShortToast(MoviesListActivity.this, error.getErrorMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    /* method for infinite scrolling, Api will be called when user reaches the bottom of the movies list */

    @Override
    public void onLoadMore(boolean isLoading) {
        if (isLoading) {
            currentPage++;
            updateFooterVisibility(true);
            callMoviesListApi(currentPage, true);
        } else
            updateFooterVisibility(false);
    }
}
