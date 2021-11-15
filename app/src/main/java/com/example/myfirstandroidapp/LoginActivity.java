package com.example.myfirstandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstandroidapp.api.RequestPlaceHolder;
import com.example.myfirstandroidapp.api.RetrofitBuilder;
import com.example.myfirstandroidapp.pojos.Login;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public EditText username,password;
    public MaterialButton loginBtn;

    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceHolder requestPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginbtn);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceHolder = retrofitBuilder.getRetrofit().create(RequestPlaceHolder.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText() != null && password.getText() != null){
                    Call<Login> loginCall = requestPlaceHolder.Login(new Login(null, username.getText().toString(),null,null, password.getText().toString()));
                    loginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if (!response.isSuccessful()){
                                if (response.code() == 404){
                                    Toast.makeText(LoginActivity.this,"User not found",Toast.LENGTH_SHORT).show();
                                    Log.e("LOGGING_ERR",response.message());
                                }else{
                                    Toast.makeText(LoginActivity.this,"There was an error upon logging the API",Toast.LENGTH_SHORT).show();
                                    Log.e("LOGGING_ERR",response.message());
                                }
                            }else{
                                if(response.code() == 200){
                                    Login loginResponse = response.body();
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    intent.putExtra("USERID",loginResponse.getId());
                                    intent.putExtra("USERNAME",loginResponse.getUsername());
                                    intent.putExtra("TOKEN",loginResponse.getToken());

                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this,"There was an error upon logging in",Toast.LENGTH_SHORT).show();
                            Log.e("LOGGING_ERR",t.getMessage());
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this,"Please supply all fields ",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
