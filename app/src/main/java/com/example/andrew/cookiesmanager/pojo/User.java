package com.example.andrew.cookiesmanager.pojo;

/**
 * Created by andrew on 02.04.16.
 */
public class User {

    private String email;
    private String id;
    private String username;
    private String photoUrl;
    private String accessToken;

    public User(String email, String username, String photoUrl) {

        this.email = email;
        this.username = username;
        this.photoUrl = photoUrl;
    }

    public User(String accessToken){
        this.accessToken = accessToken;
    }


    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
