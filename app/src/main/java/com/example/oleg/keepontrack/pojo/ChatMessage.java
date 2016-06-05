package com.example.oleg.keepontrack.pojo;

import com.google.gson.annotations.SerializedName;


public class ChatMessage {

    @SerializedName("message")
    private String chatMessage;
    @SerializedName("user")
    private User author;

    public ChatMessage(String chatMessage, User author) {
        this.chatMessage = chatMessage;
        this.author = author;
    }

    public ChatMessage() {
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
