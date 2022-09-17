package com.burung.awi;

import com.burung.awi.model.ArduinoModel;
import com.burung.awi.model.SprinklerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {
    @GET("initialize-system")
    Call<ArduinoModel> startInitialize();
    @GET("turn-on-sprinkler")
    Call<SprinklerModel> turnOnSprinkler(@Query("sprinkler") String sprinkler);
    @GET("turn-off-sprinkler")
    Call<SprinklerModel> turnOffSprinkler(@Query("sprinkler") String sprinkler);
    @GET("source-fertilizer")
    Call<ArduinoModel> fertilizerSource();
    @GET("source-water")
    Call<ArduinoModel> waterSource();
    @GET("set-system-state")
    Call<ArduinoModel> setSystemState(@Query("state") int systemState);
    @GET("get-system-state")
    Call<SprinklerModel> getSystemState();
    @GET("open-fertilizer-gate")
    Call<ArduinoModel> openFertilizer();
    @GET("close-fertilizer-gate")
    Call<ArduinoModel> closeFertilizer();



}
