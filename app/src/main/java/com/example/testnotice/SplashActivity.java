package com.example.testnotice;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    View mContentView;
    Handler handler;

    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.splash);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        mContentView = findViewById(R.id.splash);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user !=null){
                    if (user.isEmailVerified()){
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    }else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        },2000);
    }
}
/*
        This part of code uses shared preference to launch AdminHomeActivity,HomeActivity,MainActivity

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user!=null){
                    if(user.isEmailVerified()){
                        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        boolean isAdmin = prefs.getBoolean("isAdmin", false);

                        if (isAdmin) {
                            // Open AdminHomeActivity
                            Intent intent = new Intent(SplashActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                        } else {
                            // Open MainActivity or any other activity for regular users
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                    }else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        },2000);
        */