package com.example.oleg.keepontrack.pojo;

import com.google.gson.annotations.SerializedName;


public class User {

    private String email;
    private String id;
    @SerializedName("name")
    private String username;
    private String surname;
    private String skype;
    private String image;
    private String accessToken;
    private String phone;


    public User(String email, String username, String image) {

        this.email = email;
        this.username = username;
        this.image = image;
    }

    public User(String accessToken){
        this.accessToken = accessToken;
    }


    public User() {

    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;

    }

    public String getSkype() {
        return skype;
    }

    public User setSkype(String skype) {
        this.skype = skype;
        return this;

    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public User setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;

    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;

    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;

    }

    public String getImage() {
        return image;
    }

    public User setImage(String image) {
        this.image = image;
        return this;

    }
}
