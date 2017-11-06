package com.example.kevin.vamoae.api;

import com.example.kevin.vamoae.model.EventsResponse;
import com.example.kevin.vamoae.model.UserLoginResponse;
import com.example.kevin.vamoae.model.UserRegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by felix on 03/11/2017.
 */

public interface RetrofitApiPath {
    @FormUrlEncoded
    @POST("login")
    Call<UserLoginResponse> getUserLogin(@FieldMap Map<String,String> loginData);

    @FormUrlEncoded
    @POST("register")
    Call<UserRegisterResponse> setUserRegister(@FieldMap Map<String,String> registerData);

    @GET("eventos")
    Call<EventsResponse> getEvents(@Query("page") int page);
}
