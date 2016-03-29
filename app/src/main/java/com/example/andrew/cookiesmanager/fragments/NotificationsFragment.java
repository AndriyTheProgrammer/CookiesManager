package com.example.andrew.cookiesmanager.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrew.cookiesmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    View rootView;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        return rootView;
    }

}
