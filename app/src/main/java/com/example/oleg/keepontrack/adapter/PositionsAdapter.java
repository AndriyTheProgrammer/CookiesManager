package com.example.oleg.keepontrack.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class PositionsAdapter extends BaseAdapter {

    ArrayList<String> pos;

    public PositionsAdapter(ArrayList<String> pos) {
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return pos.size();
    }

    @Override
    public Object getItem(int position) {
        return pos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(parent.getContext());
        tv.setPadding(32, 32, 32, 32);
        tv.setText(pos.get(position));
        return tv;
    }
}
