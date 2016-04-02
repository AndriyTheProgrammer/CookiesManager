package com.example.andrew.cookiesmanager.pojo;

/**
 * Created by andrew on 30.03.16.
 */
public class ChatMessage {

    private String chatMessage;
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
