package com.burung.awi;

import com.burung.awi.model.ArduinoModel;
import com.burung.awi.model.SprinklerModel;
import com.burung.awi.model.TimeModel;

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
    @GET("get-automatic-time")
    Call<TimeModel> getTimeState();
    @GET("set-automatic-time")
    Call<TimeModel> setStartTimeHour(
            @Query("index") int index,
            @Query("startHour") int startHour
    );
    @GET("set-automatic-time")
    Call<TimeModel> setStartTimeMinute(
            @Query("index") int index,
            @Query("startMinute") int startHour
    );
    @GET("set-automatic-time")
    Call<TimeModel> setEndTimeHour(
            @Query("index") int index,
            @Query("endHour") int startHour
    );
    @GET("set-automatic-time")
    Call<TimeModel> setEndTimeMinute(
            @Query("index") int index,
            @Query("endMinute") int startHour
    );
    @GET("set-automatic-time-count")
    Call<TimeModel> setActiveTime(@Query("time") int time);

    @GET("open-fertilizer-gate")
    Call<ArduinoModel> openFertilizer();
    @GET("close-fertilizer-gate")
    Call<ArduinoModel> closeFertilizer();



}
