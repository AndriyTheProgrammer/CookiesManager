package com.example.oleg.keepontrack.model;

import com.example.oleg.keepontrack.model.requests.AuthRequest;
import com.example.oleg.keepontrack.model.requests.SaveProfileRequest;
import com.example.oleg.keepontrack.model.response.AuthResponse;
import com.example.oleg.keepontrack.pojo.ChatMessage;
import com.example.oleg.keepontrack.pojo.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 POST api/v1/signup - registration
 IN email, password

 POST api/v1/signin - login to get access_token
 IN email, password
 OUT api_token

 -- all next api accessible only with api_token, example: --
 -- http://46.101.139.87/api/v1/users?api_token=sfhIQYhpZxNQoKBQpztAm4Pe1lY4D9NSpwI9WzFyXVfKC3psrAobxQgNpXkO --

 -- users --

 GET api/v1/users/positions - list of user positions (pm, qa, developer..)
 GET api/v1/users/{id} - get user profile
 GET api/v1/users - get list of all users
 POST api/v1/user - update self user profile
 IN name, surname, position_id, phone, skype

 -- chats --

 GET api/v1/chat/channel/{id} - get all chat channel messages
 POST api/v1/chat/channel/{id} - post a message to a chat channel
 IN message
 GET api/v1/chat/direct/{id} - get all private channel messages
 POST api/v1/chat/direct/{id} - post a message to a private channel
 IN message
 */
public interface NetworkAPI {

//    -----Authorization
    @POST("signup")
    Call<AuthResponse> register(@Body AuthRequest authRequest);

    @POST("signin")
    Call<AuthResponse> signIn(@Body AuthRequest authRequest);

//    -----Users

    @GET("users/positions")
    Call<ResponseBody> signIn(@Query("api_token") String accessToken);

    @GET("users/{id}")
    Call<User> getUserProfile(@Path("id") String profileId, @Query("api_token") String accessToken);

    @GET("users")
    Call<ArrayList<User>> getAllUsers(@Query("api_token") String accessToken);

    @POST("user")
    Call<User> saveProfile(@Query("api_token") String accessToken,
                           @Body SaveProfileRequest saveProfileRequest);

//    -----Chat
    @GET("chat/channel/{id}")
    Call<ArrayList<ChatMessage>> getAllChatChannelMessages(@Path("id") int groupId, @Query("api_token") String accessToken);

    @POST("chat/channel/{id}")
    Call<ResponseBody> sendChannelMessage(@Path("id") int groupId, @Query("api_token") String accessToken, @Query("message") String message);

    @GET("chat/direct/{id}")
    Call<ArrayList<ChatMessage>> getAllPrivateMessages(@Path("id") int userId, @Query("api_token") String accessToken);

    @POST("chat/direct/{id}")
    Call<ResponseBody> sendPrivateMessage(@Path("id") int userId, @Query("api_token") String accessToken, @Query("message") String message);
}
