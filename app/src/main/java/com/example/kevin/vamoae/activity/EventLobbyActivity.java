package com.example.kevin.vamoae.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.adapter.EventListAdapter;
import com.example.kevin.vamoae.api.RetrofitApiCall.EventsApiCall;
import com.example.kevin.vamoae.api.RetrofitInitializer;
import com.example.kevin.vamoae.model.EventsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventLobbyActivity extends AppCompatActivity {


    @BindView(R.id.recycler_event_list)
    RecyclerView recyclerEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_lobby);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllEvents(1);
    }

    public void getAllEvents(int page) {
        Call<EventsResponse> call = new RetrofitInitializer().retrofitApiPath().getEvents(page);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                Toast.makeText(EventLobbyActivity.this, response.body().getEventsList().get(0).getName(), Toast.LENGTH_SHORT).show();
               setPhraseRecycler();

               EventListAdapter adapter = new EventListAdapter(EventLobbyActivity.this, response.body().getEventsList());
               recyclerEvent.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {

            }
        });
    }

        private void setPhraseRecycler(){
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerEvent.setLayoutManager(layoutManager);
            recyclerEvent.setNestedScrollingEnabled(false);
    }




}
