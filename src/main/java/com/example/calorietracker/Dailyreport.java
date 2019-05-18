package com.example.calorietracker;

import java.util.Date;

public class Dailyreport {
    private Usertable userid;
    private DailyreportPK dailyreportPK;
    private Integer totalCaloriesConsumed;
    private Integer totalCaloriesBurned;
    private Integer totalStepsTaken;
    private Integer calorieGoal;

    public Dailyreport(Usertable userid, DailyreportPK dailyreportPK, Integer totalCaloriesConsumed, Integer totalCaloriesBurned, Integer totalStepsTaken, Integer calorieGoal) {
        this.userid = userid;
        this.dailyreportPK = dailyreportPK;
        this.totalCaloriesConsumed = totalCaloriesConsumed;
        this.totalCaloriesBurned = totalCaloriesBurned;
        this.totalStepsTaken = totalStepsTaken;
        this.calorieGoal = calorieGoal;
    }
    public Dailyreport(){

    }

    public Usertable getUserid() {
        return userid;
    }

    public void setUserid(Usertable userid) {
        this.userid = userid;
    }

    public DailyreportPK getDailyreportPK() {
        return dailyreportPK;
    }

    public void setDailyreportPK(DailyreportPK dailyreportPK) {
        this.dailyreportPK = dailyreportPK;
    }

    public Integer getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(Integer totalCaloriesConsumed) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public Integer getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(Integer totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public Integer getTotalStepsTaken() {
        return totalStepsTaken;
    }

    public void setTotalStepsTaken(Integer totalStepsTaken) {
        this.totalStepsTaken = totalStepsTaken;
    }

    public Integer getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(Integer calorieGoal) {
        this.calorieGoal = calorieGoal;
    }
}
