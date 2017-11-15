package com.example.kevin.vamoae.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

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

    @BindView(R.id.txt_password_register_two)
    EditText txtPasswordRegisterTwo;

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

        if(validate()){
            mLoginCall = new UserApiCall();
            mLoginCall.registerUser(getRegisterData(),this);
        }

    }

    private boolean validate(){
        if(txtPasswordRegisterTwo.getText().toString().length() > 0 &&
                txtPasswordRegister.getText().toString().length() > 0 &&
                txtEmailRegister.getText().toString().length() > 0 &&
                txtNameRegister.getText().toString().length() > 0){

            if(txtPasswordRegister.getText().toString().equals(txtPasswordRegisterTwo.getText().toString())){


                if(txtEmailRegister.getText().toString().contains("@")){


                    String[] parts = txtEmailRegister.getText().toString().split("@");
                    if(parts.length == 2) {
                        String part1 = parts[0];
                        String part2 = parts[1];
                        if (part2.contains(".") && (!part2.startsWith(".")) && (!part2.endsWith(".")) && part1.length() > 0) {
                            return true;
                        }else {
                            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "As senha digitadas são diferentes", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
        }
        return false;
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
