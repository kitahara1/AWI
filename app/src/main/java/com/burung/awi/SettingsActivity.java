package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private Button btnTimeSetting;
    private Button btnFertilizerSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.text_title_bar_settings);

        btnTimeSetting = findViewById(R.id.button_time_setting);
        btnFertilizerSetting = findViewById(R.id.button_fertilizer_setting);

        btnTimeSetting.setOnClickListener(view -> {
            startActivity(new Intent(this, TimeActivity.class));
        });

        btnFertilizerSetting.setOnClickListener(v -> {
            startActivity(new Intent(this, FertilizerActivity.class));
        });

    }
}