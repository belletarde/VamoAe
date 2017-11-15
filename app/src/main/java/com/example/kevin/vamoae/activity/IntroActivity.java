package com.example.kevin.vamoae.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kevin.vamoae.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signin_user)
    public void signInOnclick(){
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btn_continue_without_login)
    public void continueOnClick(){
        Intent i = new Intent(this, EventLobbyActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.txt_to_login)
    public void goToLogin(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
