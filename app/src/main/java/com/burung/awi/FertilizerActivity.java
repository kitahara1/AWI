package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.burung.awi.constant.BaseConst;
import com.burung.awi.constant.ParameterConst;
import com.burung.awi.model.ArduinoModel;
import com.burung.awi.model.SprinklerModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FertilizerActivity extends AppCompatActivity {

    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    private Button btnOpenFertilizer;
    private Button btnCloseFertilizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);
        getSupportActionBar().setTitle(R.string.text_title_bar_fertilizer_settings);

        initializeApiResource();

        btnOpenFertilizer = findViewById(R.id.button_open_fertilizer);
        btnCloseFertilizer = findViewById(R.id.button_close_fertilizer);

        btnOpenFertilizer.setOnClickListener(v -> {
            openFertilizer();
        });

        btnCloseFertilizer.setOnClickListener(v -> {
            closeFertilizer();
        });

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

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void openFertilizer(){
        Call<ArduinoModel> call = jsonPlaceHolderAPI.openFertilizer();
        call.enqueue(new Callback<ArduinoModel>() {
            @Override
            public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                if (!response.isSuccessful()) {
                    showToast(response.message());
                }

                showToast(getResources().getString(R.string.text_success_open_fertilizer));
            }

            @Override
            public void onFailure(Call<ArduinoModel> call, Throwable t) {
               showToast(getResources().getString(R.string.text_failed));
            }
        });
    }

    private void closeFertilizer(){
        Call<ArduinoModel> call = jsonPlaceHolderAPI.closeFertilizer();
        call.enqueue(new Callback<ArduinoModel>() {
            @Override
            public void onResponse(Call<ArduinoModel> call, Response<ArduinoModel> response) {
                if (!response.isSuccessful()) {
                    showToast(response.message());
                }

                showToast(getResources().getString(R.string.text_success_close_fertilizer));
            }

            @Override
            public void onFailure(Call<ArduinoModel> call, Throwable t) {
               showToast(getResources().getString(R.string.text_failed));
            }
        });
    }
}