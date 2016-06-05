package com.example.oleg.keepontrack.adapter;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oleg.keepontrack.R;
import com.example.oleg.keepontrack.pojo.User;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private static final int DEFAULT_VIEW = 2;

    private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private View.OnClickListener onClick;
    private View view;
    private String searchQuery = "";

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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
                return new UserViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
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

    Random random = new Random();
    private void setupItemView(UserViewHolder holder, int position) {
        holder.itemView.setTag("" + users.get(position).getId());
        holder.itemView.setOnClickListener(onClick);
        if (users.get(position).getUsername() != null && !users.get(position).getUsername().equals(""))
            holder.tvUsername.setText(users.get(position).getUsername());
        else holder.tvUsername.setText(users.get(position).getEmail());
//        if (random.nextBoolean())
            holder.imageOnline.setImageDrawable(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent)));
//        else
//            holder.imageOnline.setImageDrawable(new ColorDrawable(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white)));

        if (!searchQuery.equals("") && holder.tvUsername.getText().toString().toLowerCase()
                .contains(searchQuery.toLowerCase())) {
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }else{
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUser(ArrayList<User> users) {
        this.users = users;
        this.allUsers = users;
        notifyItemRangeChanged(0, users.size());
    }

    public void addUser(User user){
        users.add(user);
        allUsers.add(user);
        notifyItemInserted(users.indexOf(user));
    }

    public void searchQuery(String searchQuery) {
        if (searchQuery != null) this.searchQuery = searchQuery;
        notifyDataSetChanged();
    }

    public void setOnClick(View.OnClickListener onClick) {

        this.onClick = onClick;
    }



    class UserViewHolder extends RecyclerView.ViewHolder {


        TextView tvUsername;
        CircleImageView imageOnline;
        public UserViewHolder(View itemView) {
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tv_chat_msg);
            imageOnline = (CircleImageView) itemView.findViewById(R.id.imageAuthor);


        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
