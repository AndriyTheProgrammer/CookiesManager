package com.example.andrew.cookiesmanager.pojo;

/**
 * Created by andrew on 02.04.16.
 */
public class User {

    private String email;
    private String username;
    private String photoUrl;

    public User(String username) {
        this.username = username;
    }

    public User(String email, String username, String photoUrl) {

        this.email = email;
        this.username = username;
        this.photoUrl = photoUrl;
    }

    public User() {

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
