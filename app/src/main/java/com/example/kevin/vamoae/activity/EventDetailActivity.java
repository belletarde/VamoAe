package com.example.kevin.vamoae.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.vamoae.Manifest;
import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.Utils.UserSingleton;
import com.example.kevin.vamoae.api.RetrofitInitializer;
import com.example.kevin.vamoae.api.RetrofithGoogleApi;
import com.example.kevin.vamoae.model.Events;
import com.example.kevin.vamoae.model.EventsResponse;
import com.example.kevin.vamoae.model.GoogleMapModel;
import com.example.kevin.vamoae.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.event_title)
    TextView eventTitle;

    @BindView(R.id.loading_view_detail)
    LinearLayout loadingView;

    @BindView(R.id.layout_detail_content)
    LinearLayout mContent;

    @BindView(R.id.img_list_event)
    ImageView imgBitMap;

    @BindView(R.id.event_desc)
    TextView descEvent;

    @BindView(R.id.start_date)
    TextView startDate;

    @BindView(R.id.end_date)
    TextView endDate;

    private String faceLink, instaLink, siteLink;
    private double lat, longi;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        UserSingleton userData = UserSingleton.getInstance();
        String token = userData.getToken();
        if (token == null){
            getMenuInflater().inflate(R.menu.menu_items, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_items_log, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:{
               shareEvent();
                break;
            }
            case android.R.id.home:{
                finish();
                break;
            } case R.id.action_login: {
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
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        id = i.getStringExtra("id");
        if (id != null) {
            getEventDetail();
        }

    }

    private void setEventDetail(Events eventDetail) {
        eventTitle.setText(eventDetail.getName().toUpperCase());
        descEvent.setText(eventDetail.getDescEvent());
        startDate.setText(eventDetail.getStartDate());
        endDate.setText(eventDetail.getFinalDate());
        Picasso.with(this).load(eventDetail.getImg()).error( R.drawable.bg_img_404 )
                .placeholder( R.drawable.bg_img_loading ).into(imgBitMap);

        faceLink = eventDetail.getFacebook();
        instaLink = eventDetail.getInstagram();
        siteLink = eventDetail.getSite();


        String address = eventDetail.getAddress()+
                " "+eventDetail.getAddressNumber()+
                " "+eventDetail.getDistrict()+
                " "+eventDetail.getCity()+
                " "+eventDetail.getUf()+
                " "+"Brasil";

        getCordinates(address);

    }

    private void getEventDetail() {

        Call<EventsResponse> call = new RetrofitInitializer().retrofitApiPath().getEventDetail(id);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                loadingView.setVisibility(View.GONE);
                mContent.setVisibility(View.VISIBLE);
                if (response.code() == 200) {
                    setEventDetail(response.body().getEventDetail());
                } else {
                    Toast.makeText(EventDetailActivity.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                loadingView.setVisibility(View.GONE);
                Toast.makeText(EventDetailActivity.this, "Problemas com a conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCordinatesApi(HashMap<String, String> map){
        Call<GoogleMapModel> call = new RetrofithGoogleApi().retrofithGooglePath().getCordinate(map);
        call.enqueue(new Callback<GoogleMapModel>() {
            @Override
            public void onResponse(Call<GoogleMapModel> call, Response<GoogleMapModel> response) {
                if(response.isSuccessful()){

                    if(response.body().getResults().size() > 0){
                        lat = Double.parseDouble(response.body().getResults().get(0).getGeometry().getLocation().getLat());
                        longi = Double.parseDouble(response.body().getResults().get(0).getGeometry().getLocation().getLng());

                    }else {
                        lat = -23.5505199;
                        longi = -46.63330939999999;
                    }
//                    SupportMapFragment mapFragment =
//                            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//                    mapFragment.getMapAsync(EventDetailActivity.this);
                }else {
                    Toast.makeText(EventDetailActivity.this, "Erro em conectar com google", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GoogleMapModel> call, Throwable t) {

            }
        });

    }
    private void getCordinates(String address){
        HashMap<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("key",getString(R.string.google_cordinates_key));
        getCordinatesApi(map);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(lat, longi);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }

    public void shareEvent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://codepath.com");
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }

    @OnClick(R.id.insta)
    public void onInstaClick(){
        Toast.makeText(this, instaLink, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.face)
    public void onFaceClick(){
        Toast.makeText(this, faceLink, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.web_site)
    public void onWebSiteClick(){
        Toast.makeText(this, siteLink, Toast.LENGTH_SHORT).show();
    }

}
