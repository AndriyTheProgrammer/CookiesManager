package com.example.oleg.keepontrack.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.oleg.keepontrack.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 17.06.16.
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatViewHolder> {

    Map<Long, String> map = new HashMap<>();
    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<String> hours = new ArrayList<>();

    @Override
    public StatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_item, parent, false));

    }

    @Override
    public void onBindViewHolder(StatViewHolder holder, int position) {
        holder.progressBar.setMax(8);
        holder.progressBar.setProgress(Integer.parseInt(hours.get(position)));
        holder.tvTime.setText(hours.get(position) + " h.");
        holder.tvDate.setText(new SimpleDateFormat("EEE MMM dd").format(dates.get(position)));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void setMap(Map<Long, String> map) {
        this.map = map;
        for (Long dateTime : map.keySet()){
            dates.add(new Date(dateTime));
            hours.add(map.get(dateTime));
        }
    }

    class StatViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        TextView tvDate;
        TextView tvTime;

        public StatViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }
}
