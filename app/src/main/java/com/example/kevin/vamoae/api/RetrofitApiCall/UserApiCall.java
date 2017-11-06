package com.example.kevin.vamoae.api.RetrofitApiCall;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.kevin.vamoae.activity.MainActivity;
import com.example.kevin.vamoae.activity.SignInActivity;
import com.example.kevin.vamoae.api.RetrofitInitializer;
import com.example.kevin.vamoae.model.UserLoginResponse;
import com.example.kevin.vamoae.model.UserRegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felix on 03/11/2017.
 */

public class UserApiCall {


    public void loginCall(Map<String,String> loginData, final MainActivity mActivity){

        final ProgressDialog pgBar = new ProgressDialog(mActivity,0);
        pgBar.setMessage("Autenticando...");
        pgBar.show();

        retrofit2.Call<UserLoginResponse> call = new RetrofitInitializer().retrofitApiPath().getUserLogin(loginData);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                pgBar.hide();
                switch (response.code()){
                    case 200:{
                        if(response.body().getSuccess() != null){
                            Toast.makeText(mActivity, response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mActivity, response.body().getError(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    default:{
                        Toast.makeText(mActivity, "Erro ao efetuar login.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserLoginResponse> call, Throwable t) {
                pgBar.hide();
                Toast.makeText(mActivity, "Erro ao tentar conectar com a internet.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerUser(Map<String,String> registerUserData, final SignInActivity mActivity){

        final ProgressDialog pgBar = new ProgressDialog(mActivity,0);
        pgBar.setMessage("Autenticando...");
        pgBar.show();

        retrofit2.Call<UserRegisterResponse> call = new RetrofitInitializer().retrofitApiPath().setUserRegister(registerUserData);
        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                pgBar.hide();

                if(response.isSuccessful()){
                        if(response.body().getError() == null){
                            Toast.makeText(mActivity, response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mActivity, "Erro inexperado ao registrar usuário.", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(mActivity, "Erro ao efetuar login.", Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                pgBar.hide();
                Toast.makeText(mActivity, "Erro ao tentar conectar com a internet.", Toast.LENGTH_SHORT).show();
                pgBar.hide();
            }
        });

    }
}
