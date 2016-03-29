package com.example.andrew.cookiesmanager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.example.andrew.cookiesmanager.utils.Convertations;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;
    MaterialEditText etEmail, etPassword;
    int sign_in = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();
        initViewsWithData();
        setUiListeners();
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void tryToSignIn() {
        startActivity(new Intent(this, MainActivity.class));
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
