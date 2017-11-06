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

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.txt_password_register)
    EditText txtPasswordRegister;

    @BindView(R.id.txt_email_register)
    EditText txtEmailRegister;

    @BindView(R.id.txt_name_register)
    EditText txtNameRegister;

    private UserApiCall mLoginCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_send_register)
    public void createAccountClick(){
        mLoginCall = new UserApiCall();
        mLoginCall.registerUser(getRegisterData(),this);
    }

    @OnClick(R.id.txt_go_to_login)
    public void goToLoginClick(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private Map<String,String> getRegisterData(){
        HashMap<String,String> registerData = new HashMap<>();
        registerData.put("name",txtNameRegister.getText().toString());
        registerData.put("email",txtEmailRegister.getText().toString());
        registerData.put("password",txtPasswordRegister.getText().toString());
        return  registerData;
    }
}
