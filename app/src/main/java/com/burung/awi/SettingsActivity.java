package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsActivity extends AppCompatActivity {

    private TextView hour1;
    private TextView hour2;
    private TextView hour3;

    private FloatingActionButton hourAdd;
    private FloatingActionButton hourDelete;

    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.text_title_bar_settings);

    }
}