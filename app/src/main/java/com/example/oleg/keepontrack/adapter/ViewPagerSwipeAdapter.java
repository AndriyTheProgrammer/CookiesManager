package com.example.oleg.keepontrack.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ViewPagerSwipeAdapter extends PagerAdapter {

    private ArrayList<View> views = new ArrayList<>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void addView(View view) {
        views.add(view);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Today";
        else if (position == 1) return "Month";
        else return "All time";
    }

    public ArrayList<View> getViews() {
        return views;
    }
}