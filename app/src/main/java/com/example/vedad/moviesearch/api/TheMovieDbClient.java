package com.example.vedad.moviesearch.api;

import com.example.vedad.moviesearch.helper.Config;
import com.example.vedad.moviesearch.models.ApiResult;
import com.example.vedad.moviesearch.models.Movie;
import com.example.vedad.moviesearch.models.TVShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Vedad on 19.10.2018..
 */

public interface TheMovieDbClient {
    @Headers("Accept: application/json")
    @GET("movie/top_rated?api_key="  + Config.apiKey)
    Call<ApiResult<Movie>> getTopRatedMovies(
    );

    @Headers("Accept: application/json")
    @GET("tv/top_rated?api_key="  + Config.apiKey)
    Call<ApiResult<TVShow>> getTopRatedTVShows(
    );

    @Headers("Accept: application/json")
    @GET("search/movie?api_key=" + Config.apiKey)
    Call<ApiResult<Movie>> searchMovies(
            @Query("query") String query
    );

    @Headers("Accept: application/json")
    @GET("search/tv?api_key=" + Config.apiKey)
    Call<ApiResult<TVShow>> searchTvShows(
            @Query("query") String query
    );
}
