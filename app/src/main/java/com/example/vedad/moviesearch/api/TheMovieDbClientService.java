package com.example.vedad.moviesearch.api;

import com.example.vedad.moviesearch.helper.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vedad on 19.10.2018..
 */

public class TheMovieDbClientService {
     static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.url)
             .addConverterFactory(GsonConverterFactory.create())
             .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
