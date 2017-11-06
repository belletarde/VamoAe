package com.example.kevin.vamoae.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.kevin.vamoae.R;
import com.example.kevin.vamoae.api.RetrofitApiCall.UserApiCall;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_user_login)
    EditText txtLoginUser;

    @BindView(R.id.txt_password_login)
    EditText txtPasswordUser;

    private UserApiCall mLoginCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_enter_account)
    public void onEnterAccountClick(){
        //requisition
        mLoginCall = new UserApiCall();
        mLoginCall.loginCall(getLoginData(), this);
    }

    @OnClick(R.id.txt_create_account)
    public void onNewAccoutClick(){
        //go to new screen
        Intent i = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(i);

    }

    private Map<String,String> getLoginData(){
        HashMap<String,String> loginData = new HashMap<>();
        loginData.put("email",txtLoginUser.getText().toString());
        loginData.put("password",txtPasswordUser.getText().toString());

        return  loginData;
    }
}
