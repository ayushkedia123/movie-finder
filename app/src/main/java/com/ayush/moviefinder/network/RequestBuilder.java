package com.ayush.moviefinder.network;


import com.ayush.moviefinder.model.Movie;
import com.ayush.moviefinder.model.MovieSearchList;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ayushkedia on 22/05/16.
 */
public interface RequestBuilder {

    @GET("/")
    void getMovieList(@Query("s") String searchtext,
                      @Query("y") String year,
                      @Query("type") String type,
                      @Query("page") String page,
                      Callback<MovieSearchList> response);

    @GET("/")
    void getMovieDetails(@Query("i") String imdbID,
                         @Query("plot") String plot,
                         Callback<Movie> response);

}
