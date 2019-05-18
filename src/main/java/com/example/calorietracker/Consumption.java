package com.example.calorietracker;

import java.util.Date;

public class Consumption {
    private Integer consumptionid;
    private Date dateofconsumption;
    private Integer quanlity;
    private Food foodid;
    private Credential userid;

    public Consumption(Integer consumptionid, Credential credential, Date dateofconsumption, Food food, Integer quanlity){
        this.consumptionid = consumptionid;
        this.dateofconsumption = dateofconsumption;
        this.quanlity = quanlity;
        this.foodid = food;
        this.userid = credential;
    }

    public Consumption(){

    }

    public Integer getConsumptionid(){
        return consumptionid;
    }

    public void setConsumptionid(Integer consumptionid){
        this.consumptionid = consumptionid;
    }

    public Date getDateofconsumption(){
        return dateofconsumption;
    }

    public void setDateofconsumption(Date dateofconsumption){
        this.dateofconsumption = dateofconsumption;
    }

    public Integer getQuanlity(){
        return quanlity;
    }

    public void setQuanlity(Integer quanlity){
        this.quanlity = quanlity;
    }

    public Food getFood(){
        return foodid;
    }

    public void setFood(Food food){
        this.foodid = food;
    }
    public Credential getCredential(){
        return userid;
    }

    public void setCredential(Credential credential){
        this.userid = credential;
    }





}
