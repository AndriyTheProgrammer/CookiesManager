package com.example.andrew.cookiesmanager.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrew.cookiesmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    View rootView;
    AppCompatSpinner spinner;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

}
