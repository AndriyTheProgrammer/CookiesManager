package com.example.oleg.keepontrack.fragments;


import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oleg.keepontrack.NetworkActivity;
import com.example.oleg.keepontrack.R;
import com.example.oleg.keepontrack.adapter.StatsAdapter;
import com.example.oleg.keepontrack.adapter.ViewPagerSwipeAdapter;
import com.example.oleg.keepontrack.database.SharedPreferencesDatabase;
import com.example.oleg.keepontrack.model.NetworkAPI;
import com.example.oleg.keepontrack.pojo.StatsResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyStatisticFragment extends Fragment {

    View rootView;
    ViewPager viewPager;
    ViewPagerSwipeAdapter viewPagerSwipeAdapter;
    TabLayout tabLayout;
    NetworkAPI backend;
    SharedPreferencesDatabase sharedPreferencesDatabase;
    Map<Long, String> statsData = new HashMap<>();



    public CompanyStatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_company_statistic, container, false);
        backend = ((NetworkActivity) getActivity()).getBackend();
        sharedPreferencesDatabase = new SharedPreferencesDatabase(getActivity());
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPagerSwipeAdapter = new ViewPagerSwipeAdapter();
        viewPager.setAdapter(viewPagerSwipeAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));

        backend.getWeeklyStats(
                26,
                sharedPreferencesDatabase.getCurrentUser().getAccessToken())
        .enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                statsData = response.body().getData();
                initDailyList();
                initMonthlyList();

            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {

            }
        });

        return rootView;
    }

    private void initMonthlyList() {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        StatsAdapter statsAdapter = new StatsAdapter();
        statsAdapter.setMap(statsData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(statsAdapter);
        viewPagerSwipeAdapter.addView(recyclerView);
    }

    private void initDailyList() {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        StatsAdapter statsAdapter = new StatsAdapter();
        HashMap<Long, String> hashMap = new HashMap<>();
        long key = 0;
        for (Long time : statsData.keySet()){
            if (key == 0) {
                key = time;

            }else
            if (time < key) key = time;
        }
        hashMap.put(key, statsData.get(key));
        statsAdapter.setMap(hashMap);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(statsAdapter);
        viewPagerSwipeAdapter.addView(recyclerView);
    }

}
