package com.example.andrew.cookiesmanager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.example.andrew.cookiesmanager.database.SharedPreferencesDatabase;
import com.example.andrew.cookiesmanager.model.NetworkAPI;
import com.example.andrew.cookiesmanager.model.requests.AuthRequest;
import com.example.andrew.cookiesmanager.model.response.AuthResponse;
import com.example.andrew.cookiesmanager.pojo.User;
import com.example.andrew.cookiesmanager.utils.Convertations;
import com.rengwuxian.materialedittext.MaterialEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    Button btnSignIn, btnSignUp;
    MaterialEditText etEmail, etPassword;
    int sign_in = -1;

    NetworkAPI backend;
    SharedPreferencesDatabase sharedPreferencesDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLogic();
        checkIsUserAuthorized();
        setContentView(R.layout.activity_login);
        findViewsById();
        initViewsWithData();
        setUiListeners();
    }

    private void checkIsUserAuthorized() {
        if (sharedPreferencesDatabase.getCurrentUser() != null) showMainScreen(false);
    }

    private void initLogic() {
        sharedPreferencesDatabase = new SharedPreferencesDatabase(this);
        backend = new Retrofit.Builder()
                .baseUrl(ApplicationConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkAPI.class);
    }

    private void findViewsById() {
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        etEmail = (MaterialEditText) findViewById(R.id.et_username);
        etPassword = (MaterialEditText) findViewById(R.id.et_password);
    }

    private void initViewsWithData() {

    }

    private void setUiListeners() {
        btnSignIn.setOnClickListener(v -> {
            if (sign_in == 1) {
                tryToSignIn();
                return;
            }
            sign_in = 1;
            switchToCredentials(btnSignIn, btnSignUp);
        });
        btnSignUp.setOnClickListener(v -> {
            if (sign_in == 2) {
                tryToSignUp();
                return;
            }
            sign_in = 2;
            switchToCredentials(btnSignUp, btnSignIn);
        });

        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                if (sign_in == 1){
                    tryToSignIn();
                }else{
                    tryToSignUp();
                }
                return true;
            }
            return false;
        });
    }

    private void tryToSignUp() {
        backend.register(new AuthRequest(etEmail.getText().toString(), etPassword.getText().toString()))
        .enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()){
                    sharedPreferencesDatabase.saveUser(new User(response.body().getAccess_token()));
                    showMainScreen(true);


                }else{
                    errorOccured(response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                errorOccured(t.getMessage());
            }
        });
    }

    private void errorOccured(String message) {
        Log.d(TAG, "Sign up error: " + message);
        Snackbar.make(btnSignIn, message, Snackbar.LENGTH_LONG)
                .show();

    }

    private void tryToSignIn() {
        backend.signIn(new AuthRequest(etEmail.getText().toString(), etPassword.getText().toString()))
        .enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()){
                    sharedPreferencesDatabase.saveUser(new User(response.body().getAccess_token()));
                    showMainScreen(false);

                }else{
                    errorOccured(response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                errorOccured(t.getMessage());
            }
        });
    }


    private void showMainScreen(boolean showProfile) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("openProfile", showProfile);
        startActivity(intent);
        finish();
    }



    private void switchToCredentials(Button btn, Button btnHide){
        AnimatorSet animatorSet = new AnimatorSet();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        ObjectAnimator btnXMovement = ObjectAnimator.ofFloat(btn, "x", btn.getX(), width - btn.getWidth() - Convertations.dpToPx(16, this));
        ObjectAnimator btnYMovement = ObjectAnimator.ofFloat(btn, "y", btn.getY(), etPassword.getY() + etPassword.getHeight() + Convertations.dpToPx(16, this));

        ValueAnimator etEmailExtension = ValueAnimator.ofInt(0, etEmail.getWidth());
        ValueAnimator etPasswordExtension = ValueAnimator.ofInt(0, etPassword.getWidth());

        etEmailExtension.addUpdateListener(animation -> {
            etEmail.getLayoutParams().width = (int) animation.getAnimatedValue();
            etEmail.requestLayout();
        });
        etPasswordExtension.addUpdateListener(animation -> {
            etPassword.getLayoutParams().width = (int) animation.getAnimatedValue();
            etPassword.requestLayout();
        });

        ObjectAnimator btnHideMovement = ObjectAnimator.ofFloat(btnHide, "x", btnHide.getX(), -btnHide.getWidth());


        animatorSet.playTogether(btnXMovement, btnYMovement, btnHideMovement, etEmailExtension, etPasswordExtension);
        animatorSet.setDuration(700);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();

        etEmail.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);


    }


}
