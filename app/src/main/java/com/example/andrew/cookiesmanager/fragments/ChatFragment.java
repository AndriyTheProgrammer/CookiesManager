package com.example.andrew.cookiesmanager.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.andrew.cookiesmanager.MainActivity;
import com.example.andrew.cookiesmanager.NetworkActivity;
import com.example.andrew.cookiesmanager.R;
import com.example.andrew.cookiesmanager.adapter.ChatAdapter;
import com.example.andrew.cookiesmanager.database.SharedPreferencesDatabase;
import com.example.andrew.cookiesmanager.model.NetworkAPI;
import com.example.andrew.cookiesmanager.pojo.ChatMessage;
import com.example.andrew.cookiesmanager.pojo.User;

import java.util.ArrayList;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private static final String TAG = ChatFragment.class.getName();
    View rootView;
    RecyclerView chatList;
    ImageView sendImage;
    EditText etChatMessage;

    ChatAdapter chatAdapter;

    NetworkAPI backend;
    SharedPreferencesDatabase sharedPreferencesDatabase;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        backend = ((NetworkActivity) getActivity()).getBackend();
        sharedPreferencesDatabase = new SharedPreferencesDatabase(getActivity());
        findViewsById();
        initViewsData();
        setUiListeners();

        getAllMessages();

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
        chatAdapter.setOnClick(v ->
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content_container, ((MainActivity) getActivity()).editProfileFragment)
                        .addToBackStack(null)
                        .commit());
        chatList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        chatList.setAdapter(chatAdapter);

    }

    private void sendNewMessage(ChatMessage chatMessage){
        backend.sendChannelMessage(1, sharedPreferencesDatabase.getCurrentUser().getAccessToken(), chatMessage.getChatMessage()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Ignore
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(rootView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        getAllMessages();
//        chatAdapter.addChatMessage(chatMessage);
    }


    private void findViewsById() {
        chatList = (RecyclerView) rootView.findViewById(R.id.chat_list);
        sendImage = (ImageView) rootView.findViewById(R.id.send_image);
        etChatMessage = (EditText) rootView.findViewById(R.id.et_chat_message);

    }

    private void getAllMessages() {
        backend.getAllChatChannelMessages(1, sharedPreferencesDatabase.getCurrentUser().getAccessToken()).enqueue(new Callback<ArrayList<ChatMessage>>() {
            @Override
            public void onResponse(Call<ArrayList<ChatMessage>> call, Response<ArrayList<ChatMessage>> response) {
                chatAdapter.setChatMessages(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ChatMessage>> call, Throwable t) {
                Snackbar.make(rootView, "Shit happens", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
