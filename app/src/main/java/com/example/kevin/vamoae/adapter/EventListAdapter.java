package com.example.kevin.vamoae.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.Utils.UserSingleton;
import com.example.kevin.vamoae.activity.EventDetailActivity;
import com.example.kevin.vamoae.api.RetrofitApiCall.LikeAndDeslikeApiCall;
import com.example.kevin.vamoae.model.Events;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{
    private Context context;
    private List<Events> eventsList;

    public EventListAdapter(Context context, List<Events> eventsList){
        this.context = context;
        this.eventsList = eventsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_list, parent, false);
        return new EventListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventListAdapter.ViewHolder holder, int position) {
        final Events event = eventsList.get(position);
        Picasso.with(context).load(event.getImg()).error( R.drawable.bg_img_404 )
                .placeholder( R.drawable.bg_img_loading ).into(holder.imgEvent);
        holder.title.setText(event.getName());
        holder.detail.setText(event.getCity()+" - "+event.getUf());
        holder.score.setText(""+ (event.getLike() - event.getDeslike()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EventDetailActivity.class);
                i.putExtra("id", event.getId());
                context.startActivity(i);
            }
        });

        if (getAlreadyLiked(event.getId()) == "like"){
            holder.like.setImageResource(R.drawable.ic_action_like_pressed);

        }else if (getAlreadyLiked(event.getId()) == "deslike"){
            holder.deslike.setImageResource(R.drawable.ic_deslike_pressed);
        }

        final HashMap<String,String> likeData = new HashMap<>();
        final UserSingleton userData = UserSingleton.getInstance();
        final String token = userData.getToken();

        final LikeAndDeslikeApiCall likeOrDeslike = new LikeAndDeslikeApiCall();

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(token != null ){
                    if(alreadyLiked(userData.getLiked(), event.getId()) == false) {
                        likeData.put("id", event.getId());
                        likeData.put("liked", "1");
                        likeData.put("api_token", token);
                        userData.setLiked(event.getId(), "like");
                        likeOrDeslike.likeCall("like", likeData, context, holder.score);

                        setLiked(holder.like, holder.deslike, "like");
                    }else {
                        Toast.makeText(context, "Ja deu like", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Você precisa estar logado para dar like.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        holder.deslike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(token != null && alreadyLiked(userData.getLiked(), event.getId()) == false){
                    likeData.put("id",event.getId());
                    likeData.put("deslike","1");
                    likeData.put("api_token",token);
                    userData.setLiked(event.getId(),"deslike");
                    likeOrDeslike.likeCall("deslike",likeData,context,holder.score);


                    setLiked(holder.like,holder.deslike, "deslike");
                }else {
                    if(token == null){
                        Toast.makeText(context, "Você precisa estar logado para dar deslike.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Ja deu deslike", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public boolean alreadyLiked(HashMap<String, String> liked, String id){
        if (liked.size() > 0) {
            for (int i = 0; i<liked.size(); i++){
                if (liked.containsKey(id)){
                    return true;
                }
            }
        }
        return false;
    }

    public void setLiked(ImageView liked, ImageView desliked, String like) {
        liked.setEnabled(false);
        desliked.setEnabled(false);
        if (like == "like") {
            liked.setImageResource(R.drawable.ic_action_like_pressed);

        }else {
            desliked.setImageResource(R.drawable.ic_deslike_pressed);
        }
    }

    public String getAlreadyLiked(String id){
        UserSingleton userData = UserSingleton.getInstance();
        HashMap<String, String> likes = userData.getLiked();
        for (int i = 0; i<likes.size(); i++){
            if (likes.containsKey(id)){
                if (likes.get(id) == "like"){
                    return "like";
                }else if(likes.get(id) == "deslike") {
                    return "deslike";
                }
            }
        }
        return "nul";
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_list_event)
        ImageView imgEvent;

        @BindView(R.id.btn_deslike_list_event)
        ImageView deslike;

        @BindView(R.id.btn_like_list_event)
        ImageView like;

        @BindView(R.id.txt_title_list_event)
        TextView title;

        @BindView(R.id.txt_detail_list_event)
        TextView detail;

        @BindView(R.id.txt_like_number_list_event)
        TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
