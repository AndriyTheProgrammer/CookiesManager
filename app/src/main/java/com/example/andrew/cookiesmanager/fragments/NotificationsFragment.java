package com.example.andrew.cookiesmanager.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrew.cookiesmanager.NetworkActivity;
import com.example.andrew.cookiesmanager.R;
import com.example.andrew.cookiesmanager.adapter.UsersNotificationAdapter;
import com.example.andrew.cookiesmanager.database.SharedPreferencesDatabase;
import com.example.andrew.cookiesmanager.model.NetworkAPI;
import com.example.andrew.cookiesmanager.pojo.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    View rootView;
    SharedPreferencesDatabase sharedPreferencesDatabase;
    NetworkAPI backend;
    RecyclerView usersNotificationList;
    UsersNotificationAdapter adapter;


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        sharedPreferencesDatabase = new SharedPreferencesDatabase(getActivity());
        backend = ((NetworkActivity) getActivity()).getBackend();

        initList();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllUsers();
    }

    private void initList() {
        usersNotificationList = (RecyclerView) rootView.findViewById(R.id.usersNotificationList);
        adapter = new UsersNotificationAdapter();
        usersNotificationList.setAdapter(adapter);
        usersNotificationList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getAllUsers() {
        backend.getAllUsers(sharedPreferencesDatabase.getCurrentUser().getAccessToken()).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                adapter.setUser(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Snackbar.make(rootView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
