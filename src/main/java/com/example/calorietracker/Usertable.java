package com.example.calorietracker;

import java.util.Date;

public class Usertable {
    private Credential credential;
    private Integer userid;
    private String userfname;
    private String userlname;
    private String email;
    private Date dob;
    private Integer height;
    private Integer weight;
    private char gender;
    private String address;
    private Integer postcode;
    private Integer levelOfActivity;
    private Integer stepPerMile;

    public Usertable(){

    }

    public Usertable(int userid){
        this.userid = userid;
    }

    public Usertable(Credential credential, Integer userid, String userfname, String userlname, String email, Date dob, Integer height, Integer weight, char gender, String address, Integer postcode, Integer levelOfActivity, Integer stepPerMile){
        this.credential = credential;
        this.userid = userid;
        this.userfname = userfname;
        this.userlname = userlname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelOfActivity = levelOfActivity;
        this.stepPerMile = stepPerMile;
    }

    public void setCredential(Credential credential){
        this.credential = credential;
    }

    public void setUserid(Integer userid){
        this.userid = userid;
    }

    public void setUserfname(String userfname){
        this.userfname = userfname;
    }

    public void setUserlname(String userlname){
        this.userlname = userlname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setDob(Date dob){
        this.dob = dob;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public void setGender(char gender){
        this.gender = gender;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setPostcode(int postcode){
        this.postcode = postcode;
    }

    public void setLevelOfActivity(int levelOfActivity){
        this.levelOfActivity = levelOfActivity;
    }

    public void setStepPerMile(int stepPerMile){
        this.stepPerMile = stepPerMile;
    }

    public Credential getCredential(){
        return credential;
    }

    public Integer getUserid(){
        return userid;
    }

    public String getUserfname(){
        return userfname;
    }

    public String getUserlname(){
        return userlname;
    }

    public String getEmail(){
        return email;
    }

    public Date getDob(){
        return dob;
    }

    public Integer getHeight(){
        return height;
    }

    public Integer getWeight(){
        return weight;
    }

    public char getGender(){
        return gender;
    }

    public String getAddress(){
        return address;
    }

    public Integer getPostcode(){
        return postcode;
    }

    public Integer getLevelOfActivity(){
        return levelOfActivity;
    }

    public Integer getStepPerMile(){
        return stepPerMile;
    }

}
