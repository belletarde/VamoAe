package com.example.kevin.vamoae.api;

import com.example.kevin.vamoae.model.GoogleMapModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by felix on 15/11/2017.
 */

public interface RetrofithGooglePath {

    @GET("geocode/json")
    Call<GoogleMapModel> getCordinate(@QueryMap HashMap<String,String> map);

}
