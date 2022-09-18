package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.burung.awi.constant.BaseConst;
import com.burung.awi.constant.ParameterConst;
import com.burung.awi.model.TimeModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimeActivity extends AppCompatActivity {
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    private TextView startHour1;
    private TextView endHour1;
    private TextView startMinute1;
    private TextView endMinute1;
    private TextView startHour2;
    private TextView endHour2;
    private TextView startMinute2;
    private TextView endMinute2;
    private TextView startHour3;
    private TextView endHour3;
    private TextView startMinute3;
    private TextView endMinute3;

    private ImageButton startHourPlus1;
    private ImageButton startHourMin1;
    private ImageButton startMinutePlus1;
    private ImageButton startMinuteMin1;
    private ImageButton endHourPlus1;
    private ImageButton endHourMin1;
    private ImageButton endMinutePlus1;
    private ImageButton endMinuteMin1;
    private ImageButton startHourPlus2;
    private ImageButton startHourMin2;
    private ImageButton startMinutePlus2;
    private ImageButton startMinuteMin2;
    private ImageButton endHourPlus2;
    private ImageButton endHourMin2;
    private ImageButton endMinutePlus2;
    private ImageButton endMinuteMin2;
    private ImageButton startHourPlus3;
    private ImageButton startHourMin3;
    private ImageButton startMinutePlus3;
    private ImageButton startMinuteMin3;
    private ImageButton endHourPlus3;
    private ImageButton endHourMin3;
    private ImageButton endMinutePlus3;
    private ImageButton endMinuteMin3;

    private ConstraintLayout time1;
    private ConstraintLayout time2;
    private ConstraintLayout time3;

    private FloatingActionButton timeAdd;
    private FloatingActionButton timeDelete;

    private int timeActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        getSupportActionBar().setTitle(R.string.text_title_bar_time_settings);

       initializeApiResource();

       startHour1 = findViewById(R.id.text_start_hour_hour_1);
       startMinute1 = findViewById(R.id.text_start_hour_minute_1);
       endHour1 = findViewById(R.id.text_end_hour_hour_1);
       endMinute1 = findViewById(R.id.text_end_hour_minute_1);
       startHour2 = findViewById(R.id.text_start_hour_hour_2);
       startMinute2 = findViewById(R.id.text_start_hour_minute_2);
       endHour2 = findViewById(R.id.text_end_hour_hour_2);
       endMinute2 = findViewById(R.id.text_end_hour_minute_2);
       startHour3 = findViewById(R.id.text_start_hour_hour_3);
       startMinute3 = findViewById(R.id.text_start_hour_minute_3);
       endHour3 = findViewById(R.id.text_end_hour_hour_3);
       endMinute3 = findViewById(R.id.text_end_hour_minute_3);

       startHourPlus1 = findViewById(R.id.btn_start_hour_plus_1);
       startHourMin1 = findViewById(R.id.btn_start_hour_min_1);
       startMinutePlus1 = findViewById(R.id.btn_start_minute_plus_1);
       startMinuteMin1 = findViewById(R.id.btn_start_minute_min_1);
       endHourPlus1 = findViewById(R.id.btn_end_hour_plus_1);
       endHourMin1 = findViewById(R.id.btn_end_hour_min_1);
       endMinutePlus1 = findViewById(R.id.btn_end_minute_plus_1);
       endMinuteMin1 = findViewById(R.id.btn_end_minute_min_1);
       startHourPlus2 = findViewById(R.id.btn_start_hour_plus_2);
       startHourMin2 = findViewById(R.id.btn_start_hour_min_2);
       startMinutePlus2 = findViewById(R.id.btn_start_minute_plus_2);
       startMinuteMin2 = findViewById(R.id.btn_start_minute_min_2);
       endHourPlus2 = findViewById(R.id.btn_end_hour_plus_2);
       endHourMin2 = findViewById(R.id.btn_end_hour_min_2);
       endMinutePlus2 = findViewById(R.id.btn_end_minute_plus_2);
       endMinuteMin2 = findViewById(R.id.btn_end_minute_min_2);
       startHourPlus3 = findViewById(R.id.btn_start_hour_plus_3);
       startHourMin3 = findViewById(R.id.btn_start_hour_min_3);
       startMinutePlus3 = findViewById(R.id.btn_start_minute_plus_3);
       startMinuteMin3 = findViewById(R.id.btn_start_minute_min_3);
       endHourPlus3 = findViewById(R.id.btn_end_hour_plus_3);
       endHourMin3 = findViewById(R.id.btn_end_hour_min_3);
       endMinutePlus3 = findViewById(R.id.btn_end_minute_plus_3);
       endMinuteMin3 = findViewById(R.id.btn_end_minute_min_3);

       timeAdd = findViewById(R.id.time_add);
       timeDelete = findViewById(R.id.time_delete);

       time1 = findViewById(R.id.time_1);
       time2 = findViewById(R.id.time_2);
       time3 = findViewById(R.id.time_3);

       startHourPlus1.setOnClickListener(view -> {
           setStartTimeHour(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_PLUS);
       });
       startHourMin1.setOnClickListener(view -> {
           setStartTimeHour(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_MIN);
       });
       startMinutePlus1.setOnClickListener(view -> {
           setStartTimeMinute(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_PLUS);
       });
       startMinuteMin1.setOnClickListener(view -> {
           setStartTimeMinute(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_MIN);
       });
       endHourPlus1.setOnClickListener(view -> {
           setEndTimeHour(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_PLUS);
       });
       endHourMin1.setOnClickListener(view -> {
           setEndTimeHour(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_MIN);
       });
       endMinutePlus1.setOnClickListener(view -> {
           setEndTimeMinute(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_PLUS);
       });
       endMinuteMin1.setOnClickListener(view -> {
           setEndTimeMinute(ParameterConst.TIME_INDEX_1, ParameterConst.TIME_MIN);
       });
       startHourPlus2.setOnClickListener(view -> {
           setStartTimeHour(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_PLUS);
       });
       startHourMin2.setOnClickListener(view -> {
           setStartTimeHour(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_MIN);
       });
       startMinutePlus2.setOnClickListener(view -> {
           setStartTimeMinute(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_PLUS);
       });
       startMinuteMin2.setOnClickListener(view -> {
           setStartTimeMinute(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_MIN);
       });
       endHourPlus2.setOnClickListener(view -> {
           setEndTimeHour(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_PLUS);
       });
       endHourMin2.setOnClickListener(view -> {
           setEndTimeHour(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_MIN);
       });
       endMinutePlus2.setOnClickListener(view -> {
           setEndTimeMinute(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_PLUS);
       });
       endMinuteMin2.setOnClickListener(view -> {
           setEndTimeMinute(ParameterConst.TIME_INDEX_2, ParameterConst.TIME_MIN);
       });
       startHourPlus3.setOnClickListener(view -> {
           setStartTimeHour(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_PLUS);
       });
       startHourMin3.setOnClickListener(view -> {
           setStartTimeHour(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_MIN);
       });
       startMinutePlus3.setOnClickListener(view -> {
           setStartTimeMinute(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_PLUS);
       });
       startMinuteMin3.setOnClickListener(view -> {
           setStartTimeMinute(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_MIN);
       });
       endHourPlus3.setOnClickListener(view -> {
           setEndTimeHour(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_PLUS);
       });
       endHourMin3.setOnClickListener(view -> {
           setEndTimeHour(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_MIN);
       });
       endMinutePlus3.setOnClickListener(view -> {
           setEndTimeMinute(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_PLUS);
       });
       endMinuteMin3.setOnClickListener(view -> {
           setEndTimeMinute(ParameterConst.TIME_INDEX_3, ParameterConst.TIME_MIN);
       });
       timeAdd.setOnClickListener(view -> {
           if (timeActive == 3) {
               makeToast(getResources().getString(R.string.text_max_time_active));
           } else {
               timeActive++;
               setActiveTime(timeActive, ParameterConst.TIME_PLUS);
           }
       });
       timeDelete.setOnClickListener(view -> {
           if (timeActive == 1) {
               makeToast(getResources().getString(R.string.text_min_time_active));
           } else {
               timeActive--;
               setActiveTime(timeActive, ParameterConst.TIME_MIN);
           }
       });

       getTimeState();

    }

    private void initializeApiResource(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
    }

    private void getTimeState(){
        Call<TimeModel> call = jsonPlaceHolderAPI.getTimeState();
        call.enqueue(new Callback<TimeModel>() {
            @Override
            public void onResponse(Call<TimeModel> call, Response<TimeModel> response) {
                if (!response.isSuccessful()) {
                    makeToast(response.message());
                }
                startHour1.setText(String.valueOf(response.body().getStartHour1()));
                startMinute1.setText(String.valueOf(response.body().getStartMinute1()));
                endHour1.setText(String.valueOf(response.body().getEndHour1()));
                endMinute1.setText(String.valueOf(response.body().getEndMinute1()));
                startHour2.setText(String.valueOf(response.body().getStartHour2()));
                startMinute2.setText(String.valueOf(response.body().getStartMinute2()));
                endHour2.setText(String.valueOf(response.body().getEndHour2()));
                endMinute2.setText(String.valueOf(response.body().getEndMinute2()));
                startHour3.setText(String.valueOf(response.body().getStartHour3()));
                startMinute3.setText(String.valueOf(response.body().getStartMinute3()));
                endHour3.setText(String.valueOf(response.body().getEndHour3()));
                endMinute3.setText(String.valueOf(response.body().getEndMinute3()));

                timeActive = response.body().getTimeActive();

                if (timeActive == 1) {
                    time2.setVisibility(time2.INVISIBLE);
                    time3.setVisibility(time3.INVISIBLE);
                } else if (timeActive == 2) {
                    time2.setVisibility(time2.VISIBLE);
                    time3.setVisibility(time3.INVISIBLE);
                } else if (timeActive == 3) {
                    time2.setVisibility(time2.VISIBLE);
                    time3.setVisibility(time3.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TimeModel> call, Throwable t) {
                makeToast(getResources().getString(R.string.text_failed));
            }
        });


    }
    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setStartTimeHour(int index, int startHour){
        Call<TimeModel> call = jsonPlaceHolderAPI.setStartTimeHour(index, startHour);
        call.enqueue(new Callback<TimeModel>() {
            @Override
            public void onResponse(Call<TimeModel> call, Response<TimeModel> response) {
                if (!response.isSuccessful()) {
                    makeToast(response.message());
                }
                getTimeState();
            }

            @Override
            public void onFailure(Call<TimeModel> call, Throwable t) {
                makeToast(getResources().getString(R.string.text_failed));
            }
        });
    }

    private void setStartTimeMinute(int index, int startMinute){
        Call<TimeModel> call = jsonPlaceHolderAPI.setStartTimeMinute(index, startMinute);
        call.enqueue(new Callback<TimeModel>() {
            @Override
            public void onResponse(Call<TimeModel> call, Response<TimeModel> response) {
                if (!response.isSuccessful()) {
                    makeToast(response.message());
                }
                getTimeState();
            }

            @Override
            public void onFailure(Call<TimeModel> call, Throwable t) {
                makeToast(getResources().getString(R.string.text_failed));
            }
        });
    }

    private void setEndTimeHour(int index, int endHour){
        Call<TimeModel> call = jsonPlaceHolderAPI.setEndTimeHour(index, endHour);
        call.enqueue(new Callback<TimeModel>() {
            @Override
            public void onResponse(Call<TimeModel> call, Response<TimeModel> response) {
                if (!response.isSuccessful()) {
                    makeToast(response.message());
                }
                getTimeState();
            }

            @Override
            public void onFailure(Call<TimeModel> call, Throwable t) {
                makeToast(getResources().getString(R.string.text_failed));
            }
        });
    }

    private void setEndTimeMinute(int index, int endMinute){
        Call<TimeModel> call = jsonPlaceHolderAPI.setEndTimeMinute(index, endMinute);
        call.enqueue(new Callback<TimeModel>() {
            @Override
            public void onResponse(Call<TimeModel> call, Response<TimeModel> response) {
                if (!response.isSuccessful()) {
                    makeToast(response.message());
                }
                getTimeState();
            }

            @Override
            public void onFailure(Call<TimeModel> call, Throwable t) {
                makeToast(getResources().getString(R.string.text_failed));
            }
        });
    }
    private void setActiveTime(int time, int state){
        Call<TimeModel> call = jsonPlaceHolderAPI.setActiveTime(time);
        call.enqueue(new Callback<TimeModel>() {
            @Override
            public void onResponse(Call<TimeModel> call, Response<TimeModel> response) {
                if (!response.isSuccessful()) {
                    makeToast(response.message());
                }
//                int checkView = time+1;
//                if (state == 1){
//                    if (checkView == 2) {
//                        time2.setVisibility(time2.VISIBLE);
//                    } else if (checkView == 3) {
//                        time3.setVisibility(time3.VISIBLE);
//                    }
//                } else if(state == 0) {
//                    if (checkView == 2) {
//                        time2.setVisibility(time2.INVISIBLE);
//                    } else if (checkView == 3) {
//                        time3.setVisibility(time3.INVISIBLE);
//                    }
//                }

                getTimeState();
            }

            @Override
            public void onFailure(Call<TimeModel> call, Throwable t) {
                makeToast(getResources().getString(R.string.text_failed));
            }
        });
    }

}