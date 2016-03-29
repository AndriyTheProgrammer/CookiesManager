package com.example.andrew.cookiesmanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrew.cookiesmanager.R;
import com.example.andrew.cookiesmanager.pojo.ChatMessage;

import java.util.ArrayList;

/**
 * Created by andrew on 30.03.16.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private static final int DEFAULT_VIEW = 2;


    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();


    @Override
    public int getItemViewType(int position) {
        if (chatMessages.isEmpty()) {
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item, parent, false);
                return new ChatMessageViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item, parent, false);
                return new ChatMessageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            setupEmptyView();
        } else if (holder instanceof ChatMessageViewHolder) {
            setupItemView((ChatMessageViewHolder) holder, position);
        }


    }

    @Override
    public int getItemCount() {
        return chatMessages.size() > 0 ? chatMessages.size() : 1;
    }

    private void setupEmptyView() {

    }

    private void setupItemView(ChatMessageViewHolder holder, int position) {

    }



    class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        public ChatMessageViewHolder(View itemView) {
            super(itemView);



        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
