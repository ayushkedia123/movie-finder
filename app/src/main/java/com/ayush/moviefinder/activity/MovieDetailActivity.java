package com.ayush.moviefinder.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayush.moviefinder.R;
import com.ayush.moviefinder.model.ErrorObject;
import com.ayush.moviefinder.model.Movie;
import com.ayush.moviefinder.network.ClientGenerator;
import com.ayush.moviefinder.network.MFResponseListener;
import com.ayush.moviefinder.network.RequestBuilder;
import com.ayush.moviefinder.utils.AppUtil;
import com.ayush.moviefinder.view.AppConstant;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvGenre;
    private TextView tvRating;
    private TextView tvPlot;
    private String imdbID;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initUI();
        callMovieDetailsApi();
    }

    /* Initialising the UI components */

    private void initUI() {
        ImageView ivPosterImage = (ImageView) findViewById(R.id.iv_movie_detail_poster);
        tvTitle = (TextView) findViewById(R.id.tv_movie_detail_title);
        tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvGenre = (TextView) findViewById(R.id.tv_genre);
        tvRating = (TextView) findViewById(R.id.tv_rating);
        tvPlot = (TextView) findViewById(R.id.tv_plot);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        imdbID = getIntent().getStringExtra(AppConstant.intentExtras.IMDB_ID);
        String imageUrl = getIntent().getStringExtra(AppConstant.intentExtras.IMAGE_URL);

        AppUtil.getInstance().loadImageGlide(this, imageUrl, ivPosterImage, R.drawable.ic_movie);
    }

    /* Calling movie details based on IMDB Id that is available in the movie list Api */

    private void callMovieDetailsApi() {

        progressBar.setVisibility(View.VISIBLE);
        final RequestBuilder requestBuilder = ClientGenerator.createService(RequestBuilder.class);
        requestBuilder.getMovieDetails(imdbID, "short", new MFResponseListener<Movie>(this) {
            @Override
            public void onSuccess(Movie result) {
                progressBar.setVisibility(View.GONE);
                if (result != null)
                    if (result.Response != null && !result.Response.equalsIgnoreCase("False")) {
                        setDataInUI(result);
                    } else
                        AppUtil.showShortToast(MovieDetailActivity.this, result.Error);

            }

            @Override
            public void onError(ErrorObject error) {
                progressBar.setVisibility(View.GONE);
                AppUtil.showShortToast(MovieDetailActivity.this, error.getErrorMessage());
            }
        });
    }

    /* Initialising the UI components */

    private void setDataInUI(Movie movie) {
        if (movie.Title != null && !movie.Title.equalsIgnoreCase("N/A")) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(movie.Title);
        } else
            tvTitle.setVisibility(View.GONE);

        if (movie.Genre != null && !movie.Genre.equalsIgnoreCase("N/A")) {
            tvGenre.setVisibility(View.VISIBLE);
            tvGenre.setText(movie.Genre);
        } else
            tvGenre.setVisibility(View.GONE);

        if (movie.Plot != null && !movie.Plot.equalsIgnoreCase("N/A")) {
            tvPlot.setVisibility(View.VISIBLE);
            tvPlot.setText(movie.Plot);
        } else
            tvPlot.setVisibility(View.GONE);

        if (movie.imdbRating != null && !movie.imdbRating.equalsIgnoreCase("N/A")) {
            tvRating.setVisibility(View.VISIBLE);
            tvRating.setText(movie.imdbRating);
        } else
            tvRating.setVisibility(View.GONE);

        if (movie.Released != null && !movie.Released.equalsIgnoreCase("N/A")) {
            tvReleaseDate.setVisibility(View.VISIBLE);
            tvReleaseDate.setText(movie.Released);
        } else
            tvReleaseDate.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
    }

    /* onClick method for back button in header */

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
