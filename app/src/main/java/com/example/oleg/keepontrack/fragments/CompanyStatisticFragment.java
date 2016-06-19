package com.example.oleg.keepontrack.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oleg.keepontrack.R;
import com.example.oleg.keepontrack.adapter.StatsAdapter;
import com.example.oleg.keepontrack.adapter.ViewPagerSwipeAdapter;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyStatisticFragment extends Fragment {

    View rootView;
    ViewPager viewPager;
    ViewPagerSwipeAdapter viewPagerSwipeAdapter;
    TabLayout tabLayout;


    public CompanyStatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_company_statistic, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPagerSwipeAdapter = new ViewPagerSwipeAdapter();
        initDailyList();
        initMonthlyList();
        viewPager.setAdapter(viewPagerSwipeAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    private void initMonthlyList() {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        StatsAdapter statsAdapter = new StatsAdapter();
        HashMap<Long, String> hashMap = new HashMap<>();
        hashMap.put(123l, "2");
        hashMap.put(5l, "4");
        hashMap.put(1000l, "7");
        statsAdapter.setMap(hashMap);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(statsAdapter);
        viewPagerSwipeAdapter.addView(recyclerView);
    }

    private void initDailyList() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.stat_item, null);
        viewPagerSwipeAdapter.addView(view);
    }

}
