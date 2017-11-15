package com.example.kevin.vamoae.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felix on 15/11/2017.
 */

public class RetrofithGoogleApi {

    private final Retrofit retrofit;

    public RetrofithGoogleApi(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofithGooglePath retrofithGooglePath() {
        return retrofit.create(RetrofithGooglePath.class);
    }

}
