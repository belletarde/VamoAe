package com.example.kevin.vamoae.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.Utils.UserSingleton;
import com.example.kevin.vamoae.adapter.EventListAdapter;
import com.example.kevin.vamoae.api.RetrofitInitializer;
import com.example.kevin.vamoae.model.Events;
import com.example.kevin.vamoae.model.EventsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventLobbyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.recycler_event_list)
    RecyclerView recyclerEvent;

    @BindView(R.id.loading_view)
    LinearLayout loadingView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private  LinearLayoutManager layoutManager;
    private int page = 1;
    private int maxPage;
    private EventListAdapter adapter;
    private List<Events> events = new ArrayList<>();
    private boolean loading = true;
    private int previousTotal = 0;
    int visibleThreshold = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_lobby);

        ButterKnife.bind(this);

        getEvents();

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
    }


    @Override
    public void onStop() {
        super.onStop();

    }

    public void setEventList(){
        adapter = new EventListAdapter(EventLobbyActivity.this, events);
        recyclerEvent.setAdapter(adapter);
        recyclerEvent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
//
//                if (loading){
//                    if (totalItemCount > previousTotal){
//                        loading = false;
//                        previousTotal = totalItemCount;
//                        page++;
//                    }
//                }
//                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
//                    getEvents();
//                    loading = true;
//                }
//
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        UserSingleton userData = UserSingleton.getInstance();
        String token = userData.getToken();
        if (token == null){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_log, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_login: {
                Intent i = new Intent(this, MainActivity.class);
                startActivityForResult(i,2);
            }
                break;
            case R.id.action_create:{
                Intent i = new Intent(this, SignInActivity.class);
                startActivityForResult(i, 2);
                break;
            }
            case R.id.action_logout: {
                UserSingleton userData = UserSingleton.getInstance();
                userData.logout();
                Intent i = new Intent(this, IntroActivity.class);
                startActivity(i);
                break;
            }
            case android.R.id.home: {
                finish();
                break;
            }

        }

        return true;
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }


    public void setRecycler(){
        layoutManager = new LinearLayoutManager(EventLobbyActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerEvent.setLayoutManager(layoutManager);
        recyclerEvent.setNestedScrollingEnabled(false);
    }

    public void getEvents() {

        Call<EventsResponse> call = new RetrofitInitializer().retrofitApiPath().getEvents(page);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                loadingView.setVisibility(View.GONE);
                recyclerEvent.setVisibility(View.VISIBLE);
                setRecycler();

                events = response.body().getEventsList();
                if(page == 1) {
                    setEventList();
                }
                else{
                    adapter.notifyItemRangeInserted(0, events.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {

            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void getEvents(int page){
        this.page = page;
        getEvents();
    }

    @Override
    public void onRefresh() {
        getEvents(1);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
