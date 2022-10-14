package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.burung.awi.constant.BaseConst;
import com.burung.awi.constant.ParameterConst;
import com.burung.awi.model.ArduinoModel;
import com.burung.awi.model.SensorModel;
import com.burung.awi.model.SprinklerModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingsActivity extends AppCompatActivity {

    private Button btnTimeSetting;
    private Button btnFertilizerSetting;
    private RadioButton radioAutoTime;
    private RadioButton radioAutoSensor;
    private RadioButton radioManual;
    private ImageButton sensorValuePlus;
    private ImageButton sensorValueMin;
    private TextView minimumSensor;
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;
    private int systemStateStatus;
    private int minimumSensorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.text_title_bar_settings);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        btnTimeSetting = findViewById(R.id.button_time_setting);
        btnFertilizerSetting = findViewById(R.id.button_fertilizer_setting);
        radioAutoSensor = findViewById(R.id.radio_auto_sensor);
        radioAutoTime = findViewById(R.id.radio_auto_time);
        radioManual = findViewById(R.id.radio_manual);
        sensorValuePlus = findViewById(R.id.btn_sensor_value_plus);
        sensorValueMin = findViewById(R.id.btn_sensor_value_min);
        minimumSensor = findViewById(R.id.text_minimum_sensor_value);

        btnTimeSetting.setOnClickListener(view -> {
            startActivity(new Intent(this, TimeActivity.class));
        });

        btnFertilizerSetting.setOnClickListener(v -> {
            startActivity(new Intent(this, FertilizerActivity.class));
        });

        radioAutoSensor.setOnClickListener(view -> {
            setSystemState(ParameterConst.SYSTEM_STATE_AUTO_SENSOR);
        });
        radioAutoTime.setOnClickListener(view -> {
            setSystemState(ParameterConst.SYSTEM_STATE_AUTO_TIME);
        });
        radioManual.setOnClickListener(view -> {
            setSystemState(ParameterConst.SYSTEM_STATE_MANUAL);
        });
        sensorValuePlus.setOnClickListener(view -> {
            setMinimumSensorUp(ParameterConst.SENSOR_VALUE_SET);
        });
        sensorValueMin.setOnClickListener(view -> {
            setMinimumSensorDown(ParameterConst.SENSOR_VALUE_SET);
        });
        getSystemState();
        getMinimumSensorValue();
    }

    private void getSystemState() {
        Call<SprinklerModel> call = jsonPlaceHolderAPI.getSystemState();
        call.enqueue(new Callback<SprinklerModel>() {
            @Override
            public void onResponse(Call<SprinklerModel> call, Response<SprinklerModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SettingsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                if (response.body().getSystemState() == ParameterConst.SYSTEM_STATE_MANUAL) {
                    radioManual.setChecked(true);
                } else if (response.body().getSystemState() == ParameterConst.SYSTEM_STATE_AUTO_SENSOR) {
                    radioAutoSensor.setChecked(true);
                }else if (response.body().getSystemState() == ParameterConst.SYSTEM_STATE_AUTO_TIME) {
                    radioAutoTime.setChecked(true);
                }
                systemStateStatus = response.body().getSystemState();
            }

            @Override
            public void onFailure(Call<SprinklerModel> call, Throwable t) {
                Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSystemState(int systemState){
        Call<ArduinoModel> call = jsonPlaceHolderAPI.setSystemState(systemState);
        call.enqueue(new Callback<ArduinoModel>() {
            @Override
            public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
                }
                systemStateStatus = systemState;
            }

            @Override
            public void onFailure(Call<ArduinoModel> call, Throwable t) {
                Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getMinimumSensorValue(){
        Call<ArduinoModel> call = jsonPlaceHolderAPI.getMinimumSensorValue();
        call.enqueue(new Callback<ArduinoModel>() {
            @Override
            public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
                }
                minimumSensorValue = (response.body().getMinimumSoilHumidity() - 200)/2;
                minimumSensor.setText(minimumSensorValue + "%");

                if (minimumSensorValue < 100) {
                    sensorValuePlus.setVisibility(View.VISIBLE);
                } else {
                    sensorValuePlus.setVisibility(View.GONE);
                }
                if (minimumSensorValue > 0) {
                    sensorValueMin.setVisibility(View.VISIBLE);
                } else {
                    sensorValueMin.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ArduinoModel> call, Throwable t) {
                Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setMinimumSensorUp(int value){
       Call<SensorModel> call = jsonPlaceHolderAPI.setMinimumSensorValueUp(value);
       call.enqueue(new Callback<SensorModel>() {
           @Override
           public void onResponse(Call<SensorModel> call, Response<SensorModel> response) {
               if (!response.isSuccessful()) {
                   Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
               }
               getMinimumSensorValue();
           }

           @Override
           public void onFailure(Call<SensorModel> call, Throwable t) {
               Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
           }
       });
    }
    private void setMinimumSensorDown(int value){
       Call<SensorModel> call = jsonPlaceHolderAPI.setMinimumSensorValueDown(value);
       call.enqueue(new Callback<SensorModel>() {
           @Override
           public void onResponse(Call<SensorModel> call, Response<SensorModel> response) {
               if (!response.isSuccessful()) {
                   Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
               }
               getMinimumSensorValue();
           }

           @Override
           public void onFailure(Call<SensorModel> call, Throwable t) {
               Toast.makeText(SettingsActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
           }
       });
    }
}