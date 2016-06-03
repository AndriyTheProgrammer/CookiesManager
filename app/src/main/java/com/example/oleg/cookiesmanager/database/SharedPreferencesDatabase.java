package com.example.oleg.cookiesmanager.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.oleg.cookiesmanager.pojo.User;
import com.google.gson.Gson;

/**
 * Created by andrew on 31.05.16.
 */
public class SharedPreferencesDatabase {

    SharedPreferences sharedPreferences;
    Gson gson;

    public SharedPreferencesDatabase(Context context){
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public User getCurrentUser(){
        return gson.fromJson(sharedPreferences.getString("user", ""), User.class);
    }

    public void saveUser(User user){
        sharedPreferences.edit()
                .putString("user", gson.toJson(user))
                .apply();
    }

    public void clearData(){
        sharedPreferences.edit().clear().apply();
    }
}
