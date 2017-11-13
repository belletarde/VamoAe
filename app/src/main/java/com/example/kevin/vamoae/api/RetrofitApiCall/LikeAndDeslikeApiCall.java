package com.example.kevin.vamoae.api.RetrofitApiCall;

import android.content.Context;
import android.widget.Toast;

import com.example.kevin.vamoae.api.RetrofitInitializer;
import com.example.kevin.vamoae.model.UserLoginResponse;

import java.util.Map;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felix on 12/11/2017.
 */

public class LikeAndDeslikeApiCall {

    public void loginCall(String likeOrDeslike,Map<String,String> loginData, final Context mActivity){

        retrofit2.Call<UserLoginResponse> call;
        if(likeOrDeslike == "like"){
            call = new RetrofitInitializer().retrofitApiPath().sendLike(loginData);

        }else {
            call = new RetrofitInitializer().retrofitApiPath().sendDeslike(loginData);

        }
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                switch (response.code()){
                    case 200:{
                        break;
                    }
                    default:{
                        Toast.makeText(mActivity, "Erro ao efetuar o like.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserLoginResponse> call, Throwable t) {

                Toast.makeText(mActivity, "Erro ao tentar conectar com a internet.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
