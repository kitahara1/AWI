package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        getSupportActionBar().setTitle(R.string.text_title_bar_time_settings);
    }
}