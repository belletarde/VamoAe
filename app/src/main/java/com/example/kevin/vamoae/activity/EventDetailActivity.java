package com.example.kevin.vamoae.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.vamoae.Manifest;
import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.api.RetrofitInitializer;
import com.example.kevin.vamoae.model.Events;
import com.example.kevin.vamoae.model.EventsResponse;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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

    Bitmap loadedImage;


    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        id = i.getStringExtra("id");
        if (id != null) {
            getEvents();
        }

    }

    private void setEventDetail(Events eventDetail) {
        eventTitle.setText(eventDetail.getName());

    }

    private void getEvents() {

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

    //TODO: exemplo de chamada de latitude e longitude
//https://maps.googleapis.com/maps/api/geocode/json?address=rua+padre+paulo+ravier+123+brasil+sao+paulo&key=AIzaSyC4uK67uZMUvRpk6f1R6p3ZJhjA97O3qco

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-23.4824827, -46.6285029);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }

    @OnClick(R.id.face)
    public void shareEvent() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), loadedImage, "", null);
        Uri screenshotUri = Uri.parse(path);

        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share image via..."));
    }

    @OnClick(R.id.insta)
    public void onShareEvent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://codepath.com");
        startActivity(Intent.createChooser(shareIntent, "Share link using"));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Picasso.with(this)
                            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png")
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            /* Save the bitmap or do something with it here */

                                    //Set it in the ImageView
                                    loadedImage = bitmap;
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
