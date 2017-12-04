package com.example.kevin.vamoae.api.RetrofitApiCall;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.vamoae.Utils.UserSingleton;
import com.example.kevin.vamoae.api.RetrofitInitializer;
import com.example.kevin.vamoae.model.LikeResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felix on 12/11/2017.
 */

public class LikeAndDeslikeApiCall {
    UserSingleton user;
    String likedDisliked = "like";

    public LikeAndDeslikeApiCall(){
         user = UserSingleton.getInstance();
    }

    private void setLikeData(String eventId, String likeOrDeslike) {

        user.setLiked(eventId, likeOrDeslike);
    }

    public void likeCall(String likeOrDeslike, final Map<String, String> likeOrDeslikeData, final Context mActivity, final TextView score) {

            retrofit2.Call<LikeResponse> call;
            if (likeOrDeslike == "like") {
                call = new RetrofitInitializer().retrofitApiPath().sendLike(likeOrDeslikeData);
                likedDisliked = "like";
            } else {
                call = new RetrofitInitializer().retrofitApiPath().sendDeslike(likeOrDeslikeData);
                likedDisliked = "deslike";
            }
            call.enqueue(new Callback<LikeResponse>() {
                @Override
                public void onResponse(retrofit2.Call<LikeResponse> call, Response<LikeResponse> response) {

                    switch (response.code()) {
                        case 200: {

                            int dLike = response.body().getEventsLikeResponse().get(0).getDeslike();
                            int like = response.body().getEventsLikeResponse().get(0).getLiked();
                            int mScore = like + (dLike * -1);
                            score.setText("" + mScore);
                            setLikeData(likeOrDeslikeData.get("id"),likedDisliked);
                            break;
                        }
                        default: {
                            Toast.makeText(mActivity, "Erro ao efetuar o like.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<LikeResponse> call, Throwable t) {

                    Toast.makeText(mActivity, "Erro ao tentar conectar com a internet.", Toast.LENGTH_SHORT).show();
                }
            });
        }


}
