package com.msc.idol.mypa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(MyPAApp.PREF_USER, MODE_PRIVATE);
                boolean isfirstLaunch = preferences.getBoolean(MyPAApp.PREF_KEY_IS_FIRST_LAUNCH, true);
                if (isfirstLaunch) {
                    startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MyPAActivity.class));
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }


}
