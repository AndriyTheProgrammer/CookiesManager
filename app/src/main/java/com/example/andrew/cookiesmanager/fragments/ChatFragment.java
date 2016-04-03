package com.example.andrew.cookiesmanager.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.andrew.cookiesmanager.MainActivity;
import com.example.andrew.cookiesmanager.R;
import com.example.andrew.cookiesmanager.adapter.ChatAdapter;
import com.example.andrew.cookiesmanager.pojo.ChatMessage;
import com.example.andrew.cookiesmanager.pojo.User;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    View rootView;
    RecyclerView chatList;
    ImageView sendImage;
    EditText etChatMessage;

    ChatAdapter chatAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        findViewsById();
        initViewsData();
        setUiListeners();

        return rootView;
    }

    private void setUiListeners() {
        sendImage.setOnClickListener(v -> {
            if (etChatMessage.length() != 0)
                sendNewMessage(new ChatMessage(etChatMessage.getText().toString(), new User()));
        });
    }

    private void initViewsData() {
        chatAdapter = new ChatAdapter();
        chatAdapter.setChatMessages(generateMockTestMessages());
        chatAdapter.setOnClick(v ->
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content_container, ((MainActivity) getActivity()).editProfileFragment)
                        .addToBackStack(null)
                        .commit());
        chatList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        chatList.setAdapter(chatAdapter);

    }

    private void sendNewMessage(ChatMessage chatMessage){
        chatAdapter.addChatMessage(chatMessage);
    }

    private ArrayList<ChatMessage> generateMockTestMessages() {
        ArrayList<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("My first message here", new User()));
        messages.add(new ChatMessage("My second message here", new User()));
        messages.add(new ChatMessage("My N message here", new User()));
        messages.add(new ChatMessage("WTF im doing?", new User()));
        return messages;
    }

    private void findViewsById() {
        chatList = (RecyclerView) rootView.findViewById(R.id.chat_list);
        sendImage = (ImageView) rootView.findViewById(R.id.send_image);
        etChatMessage = (EditText) rootView.findViewById(R.id.et_chat_message);

    }

}
