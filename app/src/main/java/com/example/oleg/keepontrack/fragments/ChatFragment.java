package com.example.oleg.keepontrack.fragments;


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

import com.example.oleg.keepontrack.MainActivity;
import com.example.oleg.keepontrack.NetworkActivity;
import com.example.oleg.keepontrack.R;
import com.example.oleg.keepontrack.adapter.ChatAdapter;
import com.example.oleg.keepontrack.database.SharedPreferencesDatabase;
import com.example.oleg.keepontrack.model.NetworkAPI;
import com.example.oleg.keepontrack.pojo.ChatMessage;
import com.example.oleg.keepontrack.pojo.StatsResponse;
import com.example.oleg.keepontrack.pojo.User;
import com.example.oleg.keepontrack.utils.ToStringConverter;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

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

    int directChatUserId = 3;
    private boolean direct = true;

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
        setDirectChat(directChatUserId);



        return rootView;
    }


    private void setUiListeners() {
        sendImage.setOnClickListener(v -> {
            if (etChatMessage.length() != 0)
                sendNewMessage(new ChatMessage(etChatMessage.getText().toString(), new User()));
            else {
                showError("Chat message cannot be empty");
            }
        });
    }

    private void showError(String errorMessage) {
        Snackbar.make(rootView, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    private void initViewsData() {
        chatAdapter = new ChatAdapter();
        chatAdapter.setOnClick(v ->{
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setId(Integer.parseInt(v.getTag().toString()));
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content_container, profileFragment)
                        .addToBackStack(null)
                        .commit();
        });
        chatList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        chatList.setAdapter(chatAdapter);

    }

    private void sendNewMessage(ChatMessage chatMessage){
        backend.sendPrivateMessage(directChatUserId, sharedPreferencesDatabase.getCurrentUser().getAccessToken(), chatMessage.getChatMessage()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Ignore
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(rootView, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        getAllMessages(direct, directChatUserId);
        etChatMessage.setText("");
//        chatAdapter.addChatMessage(chatMessage);
    }


    private void findViewsById() {
        chatList = (RecyclerView) rootView.findViewById(R.id.chat_list);
        sendImage = (ImageView) rootView.findViewById(R.id.send_image);
        etChatMessage = (EditText) rootView.findViewById(R.id.et_chat_message);

    }

    public void setDirectChat(int userId){
        direct = true;
        this.directChatUserId = userId;
        if (sharedPreferencesDatabase != null) getAllMessages(true, userId);
    }

    private void getAllMessages(boolean direct, int id) {
        if (direct){
            backend.getAllPrivateMessages(id, sharedPreferencesDatabase.getCurrentUser().getAccessToken()).enqueue(new Callback<ArrayList<ChatMessage>>() {
                @Override
                public void onResponse(Call<ArrayList<ChatMessage>> call, Response<ArrayList<ChatMessage>> response) {
                    chatAdapter.setChatMessages(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<ChatMessage>> call, Throwable t) {
                }
            });

        }else {
            backend.getAllChatChannelMessages(id, sharedPreferencesDatabase.getCurrentUser().getAccessToken()).enqueue(new Callback<ArrayList<ChatMessage>>() {
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
}
