package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FertilizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);
        getSupportActionBar().setTitle(R.string.text_title_bar_fertilizer_settings);
    }
}