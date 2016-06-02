package com.example.andrew.cookiesmanager.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrew on 02.04.16.
 */
public class User {

    private String email;
    private String id;
    @SerializedName("name")
    private String username;
    private String surname;
    private String skype;
    private String photoUrl;
    private String accessToken;
    private String phone;


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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
