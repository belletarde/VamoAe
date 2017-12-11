package com.example.kevin.vamoae.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.Utils.UserSingleton;
import com.example.kevin.vamoae.adapter.CustomList;
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

public class EventLobbyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    @BindView(R.id.recycler_event_list)
    ListView list;

    @BindView(R.id.loading_view)
    LinearLayout loadingView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.myConstraint)
    ConstraintLayout myConstraint;

    private int page = 1;

    private  boolean search = false;
    ArrayList<Events> listItem = new ArrayList<>();
    private CustomList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_lobby);

        ButterKnife.bind(this);

        if (!search){
            getEvents();
        }

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
    }


    @Override
    public void onStop() {
        super.onStop();

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

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                setEventList(listItem);
                return true;
            }
        });

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
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




    public void getEvents() {

        Call<EventsResponse> call = new RetrofitInitializer().retrofitApiPath().getEvents(page);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                loadingView.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                for ( int i =0; i < response.body().getEventsList().size(); i++) {
                    listItem.add(response.body().getEventsList().get(i));

                }
                UserSingleton user = UserSingleton.getInstance();
                if (listItem.size() <= 10) {
                    setEventList();

                    if ( listItem.size() > 5 ){
                        user.setEvents(listItem.get(4),listItem.get(2),listItem.get(3));
                    }
                }else {
                    adapter.notifyDataSetChanged();
                    if ( listItem.size() > 5 ){
                        user.setEvents(listItem.get(4),listItem.get(2),listItem.get(3));
                    }
                }

            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {

            }
        });



        mSwipeRefreshLayout.setRefreshing(false);
    }
    public void setEventList(){


        adapter = new CustomList(this, listItem);
        list.setAdapter(adapter);
        list.setOnScrollListener(new EndlessScrollListener(this));

    }

    public void setEventList(ArrayList<Events> searchList){
        adapter = new CustomList(this, searchList);
        list.setAdapter(adapter);
        list.setOnScrollListener(new EndlessScrollListener(this));

    }


    private void getEvents(int page){
        snackLoading ();
        if (!search){
            this.page = page;
            getEvents();
        }
    }

    private void snackLoading () {
        Snackbar snack = Snackbar.make(myConstraint, "Carregando mais eventos...", Snackbar.LENGTH_SHORT);
        snack.show();
    }

    @Override
    public void onRefresh() {
        listItem  = new ArrayList<>();
        page = 1;
        getEvents(1);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        ArrayList<Events> searchList = new ArrayList<>();
        for (int i = 0; i < listItem.size(); i++ ) {
            if (listItem.get(i).getName().toLowerCase().contains(s.toLowerCase()) ) {
                searchList.add(listItem.get(i));
            }
        }
        if (searchList.size() < 1 ){
            Toast.makeText(this, "Sem resultados para busca "+s , Toast.LENGTH_SHORT).show();
        } else {
            setEventList(searchList);
        }


        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }




    public class EndlessScrollListener implements AbsListView.OnScrollListener {

        private int visibleThreshold = 1;
        private int currentPage = 1;
        private int previousTotal = 0;
        private boolean loading = true;
        private Activity act;


        public EndlessScrollListener(Activity act) {
            this.act = act;
        }
        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                    currentPage++;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                getEvents(currentPage);
                loading = true;
            }
        }
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }


}
