package com.example.oleg.keepontrack.model.response;

import com.example.oleg.keepontrack.pojo.User;
import com.google.gson.annotations.SerializedName;


public class AuthResponse {

    @SerializedName("api_token")
    private String access_token;
    private User user;

    public AuthResponse(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
