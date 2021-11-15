package com.example.myfirstandroidapp.api;

import com.example.myfirstandroidapp.pojos.Login;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface RequestPlaceHolder {

        @POST("login")
        Call<Login> Login(@Body Login login);
}
