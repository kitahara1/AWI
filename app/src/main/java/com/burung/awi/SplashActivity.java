package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.burung.awi.constant.BaseConst;
import com.burung.awi.model.ArduinoModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    JsonPlaceHolderAPI jsonPlaceHolderAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseConst.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Call<ArduinoModel> call = jsonPlaceHolderAPI.getInitialState();
                call.enqueue(new Callback<ArduinoModel>() {
                    @Override
                    public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(SplashActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ArduinoModel> call, Throwable t) {
                        Toast.makeText(SplashActivity.this, R.string.text_failed, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        },3000);
    }
}