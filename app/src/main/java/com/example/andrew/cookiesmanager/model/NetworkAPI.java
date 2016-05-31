package com.example.andrew.cookiesmanager.model;

import com.example.andrew.cookiesmanager.model.requests.AuthRequest;
import com.example.andrew.cookiesmanager.model.response.AuthResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andrew on 30.05.16.
 */
public interface NetworkAPI {

//    -----Authorization
    @POST("signup")
    Call<AuthResponse> register(@Body AuthRequest authRequest);

    @POST("signin")
    Call<AuthResponse> signIn(@Body AuthRequest authRequest);

//    -----Users

    @GET("users/positions")
    Call<ResponseBody> signIn(@Query("api_token") String accessToken);

}
