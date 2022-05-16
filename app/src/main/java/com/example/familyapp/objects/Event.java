package com.example.familyapp.objects;

import com.example.familyapp.R;
import com.example.familyapp.openhelpers.OpenHelper;

public class Event extends Item {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;


    public Event(long family_id, long owner_id, int done, String description, int year, int month, int day, int hour, int minutes) {
        super(family_id, owner_id, done, description);
        this.year = year;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getItemLayout(){
        return R.layout.activity_events;
    }

    @Override
    public void setUpdateDone(OpenHelper openHelper) { }


}