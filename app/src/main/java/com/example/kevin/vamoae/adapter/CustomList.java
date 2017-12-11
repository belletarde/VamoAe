package com.example.kevin.vamoae.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.Utils.UserSingleton;
import com.example.kevin.vamoae.activity.EventDetail;
import com.example.kevin.vamoae.api.RetrofitApiCall.LikeAndDeslikeApiCall;
import com.example.kevin.vamoae.model.Events;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by felix on 10/12/2017.
 */

public class CustomList extends ArrayAdapter<Events> {

    private final Activity context;
    ArrayList<Events> objItems = new ArrayList<>();

    public CustomList(Activity context, ArrayList<Events> objItems) {
        super(context, R.layout.item_event_list, objItems);
        this.context = context;
        this.objItems = objItems;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        //--------Bind--------------------
        final Events event = objItems.get(position);
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.item_event_list, null, true);
        if (position == 0) {
            CardView mainbackground = rowView.findViewById(R.id.mainbackground);
            mainbackground.setBackgroundColor(Color.rgb(255,250,205));
        }
        ImageView imgTotal = (ImageView) rowView.findViewById(R.id.img_total_score);
        ImageView imgEvent = (ImageView) rowView.findViewById(R.id.img_list_event);
        final ImageView deslike = (ImageView) rowView.findViewById(R.id.btn_deslike_list_event);
        final ImageView like = (ImageView) rowView.findViewById(R.id.btn_like_list_event);
        TextView title = (TextView) rowView.findViewById(R.id.txt_title_list_event);
        TextView detail = (TextView) rowView.findViewById(R.id.txt_detail_list_event);
        final TextView score = (TextView) rowView.findViewById(R.id.txt_like_number_list_event);
        //---------setText----------------//

        Picasso.with(context).load(event.getImg()).error( R.drawable.bg_img_404 )
                .placeholder( R.drawable.bg_img_loading ).into(imgEvent);
        title.setText(event.getName());
        detail.setText(event.getCity()+" - "+event.getUf());
        score.setText(""+ (event.getLiked() - event.getDeslike()));

        //----------------Click-------------//

        final HashMap<String,String> likeData = new HashMap<>();
        final UserSingleton userData = UserSingleton.getInstance();
        final String token = userData.getToken();

        final LikeAndDeslikeApiCall likeOrDeslike = new LikeAndDeslikeApiCall();


        alreadyLiked2(userData.getLiked(), event.getId(), like, deslike );
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, EventDetail.class);
                i.putExtra("id", event.getId());
                i.putExtra("cheese_name", event.getName());
                context.startActivity(i);
            }
        }) ;
        if(event.getLiked() - event.getDeslike() < 0){
            imgTotal.setRotation(180);
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(token != null && alreadyLiked(userData.getLiked(), event.getId()) == false){
                    likeData.put("id", event.getId());
                    likeData.put("liked", "1");
                    likeData.put("api_token", token);
                    userData.setLiked(event.getId(), "like");
                    likeOrDeslike.likeCall("like", likeData, context, score, like);

                }else {
                    if(token == null){
                        Toast.makeText(context, "Você precisa estar logado para dar like.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Você ja avaliou este evento.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        deslike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(token != null && alreadyLiked(userData.getLiked(), event.getId()) == false){
                    likeData.put("id",event.getId());
                    likeData.put("deslike","1");
                    likeData.put("api_token",token);
                    userData.setLiked(event.getId(),"deslike");
                    likeOrDeslike.likeCall("deslike",likeData,context,score, deslike);
                }else {
                    if(token == null){
                        Toast.makeText(context, "Você precisa estar logado para dar deslike.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Você ja avaliou este evento.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rowView;
    }

    public boolean alreadyLiked(HashMap<String, String> liked, String id ){
        if (liked.size() > 0) {
            for (int i = 0; i<liked.size(); i++){
                if (liked.containsKey(id)){
                    return true;
                }
            }
        }
        return false;
    }

    public void alreadyLiked2(HashMap<String, String> liked, String id, ImageView like, ImageView deslike ){
        if (liked.size() > 0) {
            for (int i = 0; i<liked.size(); i++){
                if (liked.containsKey(id)){
                    if (liked.get(id) == "deslike") {
                        Picasso.with(context).load(R.drawable.ic_action_deslike ).into(deslike);
                    } else {
                        Picasso.with(context).load(R.drawable.ic_action_like_pressed ).into(like);
                    }
                }
            }
        }

    }


}
