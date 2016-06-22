package com.example.oleg.keepontrack.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.oleg.keepontrack.R;
import com.example.oleg.keepontrack.database.SharedPreferencesDatabase;
import com.example.oleg.keepontrack.pojo.ChatMessage;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_VIEW = 1;
    private static final int DEFAULT_VIEW = 2;


    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private View.OnClickListener onClick;


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
        Glide.with(holder.itemView.getContext())
                .load(chatMessages.get(position).getAuthor().getImage())
                .error(R.drawable.ic_person_black_24dp)
                .into(holder.imageAuthor);
        holder.tvChatMessage.setText(chatMessages.get(position).getChatMessage());
        holder.imageAuthor.setTag(R.id.action_logout, "" + chatMessages.get(position).getAuthor().getId());
        holder.imageAuthor.setOnClickListener(onClick);
        if (chatMessages.get(position).getAuthor().getId().equals(new SharedPreferencesDatabase(holder.itemView.getContext()).getCurrentUser().getId())){
            holder.tvChatMessage.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary2));
        }else{
            holder.tvChatMessage.setTextColor(Color.WHITE);
        }


    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        int preSize = this.chatMessages.size();
        this.chatMessages = chatMessages;
        notifyItemRangeChanged(0, preSize);
    }

    public void addChatMessage(ChatMessage message){
        chatMessages.add(message);
        notifyItemInserted(chatMessages.indexOf(message));
    }

    public void setOnClick(View.OnClickListener onClick){
        this.onClick = onClick;
    }

    class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        TextView tvChatMessage;
        CircleImageView imageAuthor;
        public ChatMessageViewHolder(View itemView) {
            super(itemView);

            tvChatMessage = (TextView) itemView.findViewById(R.id.tv_chat_msg);
            imageAuthor = (CircleImageView) itemView.findViewById(R.id.imageAuthor);



        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
