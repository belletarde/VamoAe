package com.example.kevin.vamoae.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.model.Events;
import com.squareup.picasso.Picasso;

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
        Events event = eventsList.get(position);
        Picasso.with(context).load(event.getImg()).into(holder.imgEvent);
        holder.title.setText(event.getName());
        holder.detail.setText(event.getCity()+" - "+event.getUf());
        holder.score.setText("total: "+ (event.getLike() - event.getDeslike()));
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
