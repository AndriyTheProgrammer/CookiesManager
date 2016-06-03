package com.example.oleg.cookiesmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oleg.cookiesmanager.R;
import com.example.oleg.cookiesmanager.pojo.User;

import java.util.ArrayList;

/**
 * Created by andrew on 02.04.16.
 */
public class UsersNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private static final int DEFAULT_VIEW = 2;

    private ArrayList<User> users = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if (users.isEmpty()) {
            return EMPTY_VIEW;
        } else {
            return DEFAULT_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case EMPTY_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_empty_view, parent, false);
                return new EmptyViewHolder(view);
            case DEFAULT_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_notification_item, parent, false);
                return new UserViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_notification_item, parent, false);
                return new UserViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            setupEmptyView();
        } else if (holder instanceof UserViewHolder) {
            setupItemView((UserViewHolder) holder, position);
        }


    }

    @Override
    public int getItemCount() {
        return users.size() > 0 ? users.size() : 1;
    }

    private void setupEmptyView() {

    }

    private void setupItemView(UserViewHolder holder, int position) {
        if (users.get(position).getUsername() != null && !users.get(position).getUsername().equals(""))
            holder.tvUsername.setText(users.get(position).getUsername());
        else holder.tvUsername.setText(users.get(position).getEmail());
    }


    public void setUser(ArrayList<User> users) {
        this.users = users;
        notifyItemRangeChanged(0, users.size());
    }




    class UserViewHolder extends RecyclerView.ViewHolder {


        TextView tvUsername;
        public UserViewHolder(View itemView) {
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tvName);


        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
