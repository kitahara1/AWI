package com.burung.awi.model;

import com.google.gson.annotations.SerializedName;

public class TimeModel {
    private String responseMessage;
    private String statusCode;
    private int startHour1;
    private int startMinute1;
    private int endHour1;
    private int endMinute1;
    private int startHour2;
    private int startMinute2;
    private int endHour2;
    private int endMinute2;
    private int startHour3;
    private int startMinute3;
    private int endHour3;
    private int endMinute3;
    @SerializedName("time")
    private int timeActive;


    public String getResponseMessage() {
        return responseMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public int getStartHour1() {
        return startHour1;
    }

    public int getStartMinute1() {
        return startMinute1;
    }

    public int getEndHour1() {
        return endHour1;
    }

    public int getEndMinute1() {
        return endMinute1;
    }

    public int getStartHour2() {
        return startHour2;
    }

    public int getStartMinute2() {
        return startMinute2;
    }

    public int getEndHour2() {
        return endHour2;
    }

    public int getEndMinute2() {
        return endMinute2;
    }

    public int getStartHour3() {
        return startHour3;
    }

    public int getStartMinute3() {
        return startMinute3;
    }

    public int getEndHour3() {
        return endHour3;
    }

    public int getEndMinute3() {
        return endMinute3;
    }

    public int getTimeActive() {
        return timeActive;
    }
}
