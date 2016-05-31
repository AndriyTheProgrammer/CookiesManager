package com.example.andrew.cookiesmanager.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrew on 30.05.16.
 */
public class AuthResponse {

    @SerializedName("api_token")
    private String access_token;

    public AuthResponse(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
