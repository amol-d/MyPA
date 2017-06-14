package com.msc.idol.mypa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView name = (TextView) findViewById(R.id.name);
        TextView city = (TextView) findViewById(R.id.city);
        TextView mobile = (TextView) findViewById(R.id.mobile);

        SharedPreferences preferences = getSharedPreferences(MyPAApp.PREF_USER, MODE_PRIVATE);
        name.setText(preferences.getString(MyPAApp.PREF_KEY_NAME, "User"));
        city.setText(preferences.getString(MyPAApp.PREF_KEY_CITY, "City"));
        mobile.setText(preferences.getString(MyPAApp.PREF_KEY_MOBILE, "Mobile"));
    }
}
