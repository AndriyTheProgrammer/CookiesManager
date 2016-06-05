package com.example.oleg.keepontrack.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.oleg.keepontrack.NetworkActivity;
import com.example.oleg.keepontrack.R;
import com.example.oleg.keepontrack.adapter.PositionsAdapter;
import com.example.oleg.keepontrack.database.SharedPreferencesDatabase;
import com.example.oleg.keepontrack.model.NetworkAPI;
import com.example.oleg.keepontrack.model.requests.SaveProfileRequest;
import com.example.oleg.keepontrack.pojo.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    View rootView;
    Spinner spinner;
    Button btnSave;
    NetworkAPI backend;
    SharedPreferencesDatabase sharedPreferencesDatabase;
    EditText etName, etSurname, etPhone, etSkype, etEmail;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_edit_profile, container, false);
        findViewsById();
        setUiListeners();
        backend = ((NetworkActivity) getActivity()).getBackend();
        sharedPreferencesDatabase = new SharedPreferencesDatabase(getActivity());


        ArrayList<String> pos = new ArrayList<>();
        pos.add("Developer");
        pos.add("Designer");
        pos.add("PM");
        pos.add("QA");
        spinner.setAdapter(new PositionsAdapter(pos));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserProfile();
    }

    private void setUiListeners() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }



    private void findViewsById() {
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        etName = (EditText) rootView.findViewById(R.id.etName);
        etSurname = (EditText) rootView.findViewById(R.id.etSurname);
        etPhone = (EditText) rootView.findViewById(R.id.etPhone);
        etSkype = (EditText) rootView.findViewById(R.id.etSkype);
    }

    private void getUserProfile() {
        backend.getUserProfile(sharedPreferencesDatabase.getCurrentUser().getId(),
                sharedPreferencesDatabase.getCurrentUser().getAccessToken()).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        updateUiWithUser(response.body());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Snackbar.make(rootView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void updateUiWithUser(User user){
        if (user.getUsername() != null) etName.setText(user.getUsername());
        if (user.getSurname() != null) etSurname.setText(user.getSurname());
        if (user.getPhone() != null) etPhone.setText(user.getPhone());
        if (user.getSkype() != null) etSkype.setText(user.getSkype());
    }

    private void saveProfile() {

        SaveProfileRequest saveProfileRequest = new SaveProfileRequest();
        if (etName.getText().toString().length() != 0) saveProfileRequest.setName(etName.getText().toString());
        if (etSurname.getText().toString().length() != 0) saveProfileRequest.setSurname(etSurname.getText().toString());
//        if (spinner.getSelectedItem().toString().length() != 0) saveProfileRequest.setPosition_id(spinner.getSelectedItem().toString());
        if (etPhone.getText().toString().length() != 0) saveProfileRequest.setPhone(etPhone.getText().toString());
        if (etSkype.getText().toString().length() != 0) saveProfileRequest.setSkype(etSkype.getText().toString());

        backend.saveProfile(sharedPreferencesDatabase.getCurrentUser().getAccessToken(),
                saveProfileRequest
                ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getUserProfile();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
