package com.example.calorietracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class StepsOfDay {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "userid")
    public int userid;

    @ColumnInfo(name = "stepsofperiod")
    public int stepsofperiod;

    @ColumnInfo(name = "time_of_step")
    public String time;


    public StepsOfDay(int userid, int stepsofperiod, String time) {
        this.userid = userid;
        this.stepsofperiod=stepsofperiod;
        this.time=time;
    }
    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getStepsofperiod() {
        return stepsofperiod;
    }
    public void setStepsofperiod(int stepsofperiod) {
        this.stepsofperiod = stepsofperiod;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String date) {
        this.time = time;
    } }
