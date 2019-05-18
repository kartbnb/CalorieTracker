package com.example.calorietracker;

public class Food {
    private Integer foodid;
    private String foodname;
    private String category;
    private Integer calorie;
    private String servingUnit;
    private Integer fat;
    private Double servingAmount;

    public Food(int foodid, String foodname, String category, int calorie, String servingUnit, int fat, double servingAmount){
        this.foodid = foodid;
        this.foodname = foodname;
        this.category = category;
        this.calorie = calorie;
        this.servingAmount = servingAmount;
        this.fat = fat;
        this.servingAmount = servingAmount;
    }

    public Food(Integer foodid) {
        this.foodid = foodid;
    }


    public Integer getFoodid(){
        return foodid;
    }

    public void setFoodid(Integer foodid){
        this.foodid = foodid;
    }

    public String getFoodname(){
        return foodname;
    }

    public void setFoodname(String foodname){
        this.foodname = foodname;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public Integer getCalorie(){
        return calorie;
    }

    public void setCalorie(Integer calorie){
        this.calorie = calorie;
    }

    public String getServingUnit(){
        return servingUnit;
    }

    public void setServingUnit(String servingUnit){
        this.servingUnit = servingUnit;
    }

    public Integer getFat(){
        return fat;
    }

    public void setFat(Integer fat){
        this.fat = fat;
    }

    public Double getServingAmount(){
        return servingAmount;
    }

    public void getServingAmount(Double servingAmount){
        this.servingAmount = servingAmount;
    }
}
