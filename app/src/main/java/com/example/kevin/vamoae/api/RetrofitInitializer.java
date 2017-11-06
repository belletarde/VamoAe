package com.example.kevin.vamoae.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felix on 03/11/2017.
 */

public class RetrofitInitializer {

    private final Retrofit retrofit;

    public RetrofitInitializer(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://vamoae.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofitApiPath retrofitApiPath() {
        return retrofit.create(RetrofitApiPath.class);
    }

}
