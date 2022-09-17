package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.burung.awi.constant.BaseConst;
import com.burung.awi.constant.ParameterConst;
import com.burung.awi.constant.ResponseConst;
import com.burung.awi.model.ArduinoModel;
import com.burung.awi.model.SprinklerModel;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textStatus;
    private TextView sprayIndicatorLeft1;
    private TextView sprayIndicatorRight1;
    private TextView sprayIndicatorLeft2;
    private TextView sprayIndicatorRight2;
    private TextView sprayIndicatorLeft3;
    private TextView sprayIndicatorRight3;
    private TextView signalStatus;
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;


    private Button btnSprayLeft1;
    private Button btnSprayRight1;
    private Button btnSprayLeft2;
    private Button btnSprayRight2;
    private Button btnSprayLeft3;
    private Button btnSprayRight3;
    private Button btnSprayLeftAll;
    private Button btnSprayRightAll;

    private ConstraintLayout containerSprayL1;
    private ConstraintLayout containerSprayR1;
    private ConstraintLayout containerSprayL2;
    private ConstraintLayout containerSprayR2;
    private ConstraintLayout containerSprayL3;
    private ConstraintLayout containerSprayR3;

    private ImageButton btnSettings;

    private RadioButton radioWaterSource;
    private RadioButton radioFertilizerSource;
    private RadioButton radioAuto;
    private RadioButton radioManual;

    private boolean initializeState = false;
    private int sprinklerLeft1Status;
    private int sprinklerRight1Status;
    private int sprinklerLeft2Status;
    private int sprinklerRight2Status;
    private int sprinklerLeft3Status;
    private int sprinklerRight3Status;
    private int sprinklerLeftAllStatus;
    private int sprinklerRightAllStatus;
    private int systemStateStatus;

    private Handler mHandler;
    private long pingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textStatus = findViewById(R.id.text_status);
        signalStatus = findViewById(R.id.signal_status);

        sprayIndicatorLeft1 = findViewById(R.id.spray_indicator_left_1);
        sprayIndicatorRight1 = findViewById(R.id.spray_indicator_right_1);
        sprayIndicatorLeft2 = findViewById(R.id.spray_indicator_left_2);
        sprayIndicatorRight2 = findViewById(R.id.spray_indicator_right_2);
        sprayIndicatorLeft3 = findViewById(R.id.spray_indicator_left_3);
        sprayIndicatorRight3 = findViewById(R.id.spray_indicator_right_3);


        btnSprayLeft1 = findViewById(R.id.btn_spray_left_1);
        btnSprayRight1 = findViewById(R.id.btn_spray_right_1);
        btnSprayLeft2 = findViewById(R.id.btn_spray_left_2);
        btnSprayRight2 = findViewById(R.id.btn_spray_right_2);
        btnSprayLeft3 = findViewById(R.id.btn_spray_left_3);
        btnSprayRight3 = findViewById(R.id.btn_spray_right_3);
        btnSprayLeftAll = findViewById(R.id.btn_spray_left_all);
        btnSprayRightAll = findViewById(R.id.btn_spray_right_all);

        containerSprayL1 = findViewById(R.id.container_spray_l1);
        containerSprayR1 = findViewById(R.id.container_spray_r1);
        containerSprayL2 = findViewById(R.id.container_spray_l2);
        containerSprayR2 = findViewById(R.id.container_spray_r2);
        containerSprayL3 = findViewById(R.id.container_spray_l3);
        containerSprayR3 = findViewById(R.id.container_spray_r3);

        btnSettings = findViewById(R.id.btn_settings);

        radioWaterSource = findViewById(R.id.radio_water_source);
        radioFertilizerSource = findViewById(R.id.radio_fertilizer_source);
        radioAuto = findViewById(R.id.radio_auto);
        radioManual = findViewById(R.id.radio_manual);

        sprinklerLeft1Status = 91;
        sprinklerRight1Status = 91;
        sprinklerLeft2Status = 91;
        sprinklerRight2Status = 91;
        sprinklerLeft3Status = 91;
        sprinklerRight3Status = 91;
        sprinklerLeftAllStatus = 91;
        sprinklerRightAllStatus = 91;



        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        containerSprayL1.setOnClickListener(view -> {
            if (sprinklerLeft1Status == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_LEFT_1);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_LEFT_1);
            }
        });
        containerSprayR1.setOnClickListener(view -> {
            if (sprinklerRight1Status == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_RIGHT_1);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_RIGHT_1);
            }
        });
        containerSprayL2.setOnClickListener(view -> {
            if (sprinklerLeft2Status == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_LEFT_2);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_LEFT_2);
            }
        });
        containerSprayR2.setOnClickListener(view -> {
            if (sprinklerRight2Status == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_RIGHT_2);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_RIGHT_2);
            }
        });
        containerSprayL3.setOnClickListener(view -> {
            if (sprinklerLeft3Status == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_LEFT_3);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_LEFT_3);
            }
        });
        containerSprayR3.setOnClickListener(view -> {
            if (sprinklerRight3Status == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_RIGHT_3);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_RIGHT_3);
            }
        });
        btnSprayLeftAll.setOnClickListener(view -> {
            if (sprinklerLeftAllStatus == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_LEFT_ALL);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_LEFT_ALL);
            }
        });
        btnSprayRightAll.setOnClickListener(view -> {
            if (sprinklerRightAllStatus == ResponseConst.INACTIVE) {
                turnOnSprinkler(ParameterConst.SPRINKLER_RIGHT_ALL);
            } else {
                turnOffSprinkler(ParameterConst.SPRINKLER_RIGHT_ALL);
            }
        });

        radioWaterSource.setOnClickListener(view -> {
            waterSource();
        });
        radioFertilizerSource.setOnClickListener(view -> {
            fertilizerSource();
        });
        radioAuto.setOnClickListener(view -> {
            setSystemState(ParameterConst.SYSTEM_STATE_AUTO);
        });
        radioManual.setOnClickListener(view -> {
            setSystemState(ParameterConst.SYSTEM_STATE_MANUAL);
        });

        btnSettings.setOnClickListener(view -> {
            startSettings();
        });
        getSystemState();
        mHandler = new Handler();
        mPingChecker.run();

    }
    private void getSystemState() {
        Call<SprinklerModel> call = jsonPlaceHolderAPI.getSystemState();
        call.enqueue(new Callback<SprinklerModel>() {
            @Override
            public void onResponse(Call<SprinklerModel> call, Response<SprinklerModel> response) {
                if (!response.isSuccessful()) {
                    textStatus.setText(response.message());
                }
                if (response.body().getSystemState() == ParameterConst.SYSTEM_STATE_MANUAL) {
                    radioManual.setChecked(true);
                } else {
                    radioAuto.setChecked(true);
                }
                systemStateStatus = response.body().getSystemState();
            }

            @Override
            public void onFailure(Call<SprinklerModel> call, Throwable t) {
                textStatus.setText(R.string.text_failed);
            }
        });
    }

    Runnable mPingChecker = new Runnable() {
        @Override
        public void run() {
            Runtime runtime = Runtime.getRuntime();
            try {
                long a = System.currentTimeMillis() % 1000;
                Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + BaseConst.BASE_URL);
                ipProcess.waitFor();
                pingTime = System.currentTimeMillis() % 1000 - a;
                signalStatus.setText(pingTime + " ms");
            } catch (IOException e){
                textStatus.setText(e.getMessage());
            } catch (InterruptedException e) {
                textStatus.setText(e.getMessage());
            } finally {
                mHandler.postDelayed(mPingChecker,10000);
            }
        }
    };

    private void turnOnSprinkler(String sprinkler) {
        Call<SprinklerModel> call = jsonPlaceHolderAPI.turnOnSprinkler(sprinkler);
        call.enqueue(new Callback<SprinklerModel>() {
            @Override
            public void onResponse(Call<SprinklerModel> call, Response<SprinklerModel> response) {
                if (!response.isSuccessful()) {
                    textStatus.setText(response.message());
                }
                mapSprinklerState(sprinkler, response.body().getState());
                textStatus.setText(R.string.text_success);
            }

            @Override
            public void onFailure(Call<SprinklerModel> call, Throwable t) {
                textStatus.setText(R.string.text_failed);
            }
        });
    }
    private void turnOffSprinkler(String sprinkler) {
        Call<SprinklerModel> call = jsonPlaceHolderAPI.turnOffSprinkler(sprinkler);
        call.enqueue(new Callback<SprinklerModel>() {
            @Override
            public void onResponse(Call<SprinklerModel> call, Response<SprinklerModel> response) {
                if (!response.isSuccessful()) {
                    textStatus.setText(response.message());
                }
                mapSprinklerState(sprinkler, response.body().getState());
                textStatus.setText(R.string.text_success);
            }

            @Override
            public void onFailure(Call<SprinklerModel> call, Throwable t) {
                textStatus.setText(R.string.text_failed);
            }
        });
    }


    private void mapSprinklerState(String sprinkler, int state) {
        switch (sprinkler) {
            case ParameterConst.SPRINKLER_LEFT_1:
                sprinklerLeft1Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayLeft1.setText(R.string.text_stop_spray);
                    sprayIndicatorLeft1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayLeft1.setText(R.string.text_spray);
                    sprayIndicatorLeft1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
            case ParameterConst.SPRINKLER_RIGHT_1:
                sprinklerRight1Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayRight1.setText(R.string.text_stop_spray);
                    sprayIndicatorRight1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayRight1.setText(R.string.text_spray);
                    sprayIndicatorRight1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
            case ParameterConst.SPRINKLER_LEFT_2:
                sprinklerLeft2Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayLeft2.setText(R.string.text_stop_spray);
                    sprayIndicatorLeft2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayLeft2.setText(R.string.text_spray);
                    sprayIndicatorLeft2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
            case ParameterConst.SPRINKLER_RIGHT_2:
                sprinklerRight2Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayRight2.setText(R.string.text_stop_spray);
                    sprayIndicatorRight2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayRight2.setText(R.string.text_spray);
                    sprayIndicatorRight2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
            case ParameterConst.SPRINKLER_LEFT_3:
                sprinklerLeft3Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayLeft3.setText(R.string.text_stop_spray);
                    sprayIndicatorLeft3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayLeft3.setText(R.string.text_spray);
                    sprayIndicatorLeft3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
            case ParameterConst.SPRINKLER_RIGHT_3:
                sprinklerRight3Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayRight3.setText(R.string.text_stop_spray);
                    sprayIndicatorRight3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayRight3.setText(R.string.text_spray);
                    sprayIndicatorRight3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
            case ParameterConst.SPRINKLER_LEFT_ALL:
                sprinklerLeftAllStatus = state;
                sprinklerLeft1Status = state;
                sprinklerLeft2Status = state;
                sprinklerLeft3Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayLeft1.setText(R.string.text_stop_spray);
                    btnSprayLeft2.setText(R.string.text_stop_spray);
                    btnSprayLeft3.setText(R.string.text_stop_spray);
                    btnSprayLeftAll.setText(R.string.text_stop_spray);
                    sprayIndicatorLeft1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                    sprayIndicatorLeft2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                    sprayIndicatorLeft3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayLeft1.setText(R.string.text_spray);
                    btnSprayLeft2.setText(R.string.text_spray);
                    btnSprayLeft3.setText(R.string.text_spray);
                    btnSprayLeftAll.setText(R.string.text_spray);
                    sprayIndicatorLeft1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                    sprayIndicatorLeft2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                    sprayIndicatorLeft3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
            case ParameterConst.SPRINKLER_RIGHT_ALL:
                sprinklerRightAllStatus = state;
                sprinklerRight1Status = state;
                sprinklerRight2Status = state;
                sprinklerRight3Status = state;
                if (state == ResponseConst.ACTIVE) {
                    btnSprayRight1.setText(R.string.text_stop_spray);
                    btnSprayRight2.setText(R.string.text_stop_spray);
                    btnSprayRight3.setText(R.string.text_stop_spray);
                    btnSprayRightAll.setText(R.string.text_stop_spray);
                    sprayIndicatorRight1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                    sprayIndicatorRight2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                    sprayIndicatorRight3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_on ));
                } else {
                    btnSprayRight1.setText(R.string.text_spray);
                    btnSprayRight2.setText(R.string.text_spray);
                    btnSprayRight3.setText(R.string.text_spray);
                    btnSprayRightAll.setText(R.string.text_spray);
                    sprayIndicatorRight1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                    sprayIndicatorRight2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                    sprayIndicatorRight3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.spray_indicator_off ));
                }
                break;
        }
        if (sprinklerRight1Status == ResponseConst.INACTIVE && sprinklerRight2Status == ResponseConst.INACTIVE && sprinklerRight3Status == ResponseConst.INACTIVE) {
            btnSprayRightAll.setText(R.string.text_spray);
            sprinklerRightAllStatus = ResponseConst.INACTIVE;
        }
        if (sprinklerLeft1Status == ResponseConst.INACTIVE && sprinklerLeft2Status == ResponseConst.INACTIVE && sprinklerLeft3Status == ResponseConst.INACTIVE) {
            btnSprayLeftAll.setText(R.string.text_spray);
            sprinklerLeftAllStatus = ResponseConst.INACTIVE;
        }
        if (sprinklerRight1Status == ResponseConst.ACTIVE && sprinklerRight2Status == ResponseConst.ACTIVE && sprinklerRight3Status == ResponseConst.ACTIVE) {
            btnSprayRightAll.setText(R.string.text_stop_spray);
            sprinklerRightAllStatus = ResponseConst.ACTIVE;
        }
        if (sprinklerLeft1Status == ResponseConst.ACTIVE && sprinklerLeft2Status == ResponseConst.ACTIVE && sprinklerLeft3Status == ResponseConst.ACTIVE) {
            btnSprayLeftAll.setText(R.string.text_stop_spray);
            sprinklerLeftAllStatus = ResponseConst.ACTIVE;
        }
        if (systemStateStatus == ParameterConst.SYSTEM_STATE_AUTO){
            getSystemState();
        }
    }
    private void waterSource() {
        Call<ArduinoModel> call = jsonPlaceHolderAPI.waterSource();
        call.enqueue(new Callback<ArduinoModel>() {
            @Override
            public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                if (!response.isSuccessful()) {
                    textStatus.setText(response.message());
                }
                textStatus.setText(R.string.text_success);
            }

            @Override
            public void onFailure(Call<ArduinoModel> call, Throwable t) {
                textStatus.setText(R.string.text_failed);
            }
        });

    }
    private void fertilizerSource() {
        Call<ArduinoModel> call = jsonPlaceHolderAPI.fertilizerSource();
        call.enqueue(new Callback<ArduinoModel>() {
            @Override
            public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                if (!response.isSuccessful()) {
                    textStatus.setText(response.message());
                }
                textStatus.setText(R.string.text_success);
            }

            @Override
            public void onFailure(Call<ArduinoModel> call, Throwable t) {
                textStatus.setText(R.string.text_failed);
            }
        });
    }
    private void setSystemState(int systemState){
        Call<ArduinoModel> call = jsonPlaceHolderAPI.setSystemState(systemState);
        call.enqueue(new Callback<ArduinoModel>() {
            @Override
            public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                if (!response.isSuccessful()) {
                    textStatus.setText(response.message());
                }
                systemStateStatus = systemState;
                textStatus.setText(R.string.text_success);
            }

            @Override
            public void onFailure(Call<ArduinoModel> call, Throwable t) {
                textStatus.setText(R.string.text_failed);

            }
        });
    }
    public void startSettings() {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }
}