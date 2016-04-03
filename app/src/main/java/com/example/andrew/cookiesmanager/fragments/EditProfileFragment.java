package com.example.andrew.cookiesmanager.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.andrew.cookiesmanager.R;
import com.example.andrew.cookiesmanager.adapter.PositionsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    View rootView;
    Spinner spinner;
    Button btnCall;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_edit_profile, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        btnCall = (Button) rootView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:380999999999"));
            startActivity(intent);
        });
        ArrayList<String> pos = new ArrayList<>();
        pos.add("Developer");
        pos.add("Designer");
        pos.add("PM");
        pos.add("QA");
        spinner.setAdapter(new PositionsAdapter(pos));
        return rootView;
    }

}
