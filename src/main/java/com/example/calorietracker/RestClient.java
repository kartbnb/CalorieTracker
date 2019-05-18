package com.example.calorietracker;


import java.net.InetAddress;
import android.os.HandlerThread;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {

    private static  String BASE_URL = "http://192.168.1.111:8080/FatCal/webresources/";


    public static String findCredential(int userid) {
        final String methodPath = "fatcal.credential/" + userid;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = new String();
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findByUserid(int userid) {
        final String methodPath = "fatcal.usertable/" + userid;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = new String();
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }

        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findByUsername(String username){
        final String methodPath = "fatcal.credential/findByUsername/" + username;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = new String();
        //String userid = new String();
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
        //return userid;
    }

    public static void createConsumption(Consumption consumption){
        final String methodPath = "fatcal.consumption/";

        URL url = null;
        HttpURLConnection conn = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson = gson.toJson(consumption);
            System.out.println(stringUserJson);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createUsertable(Usertable usertable) {
        final String methodPath = "fatcal.usertable/";

        URL url = null;
        HttpURLConnection conn = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson = gson.toJson(usertable);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createCredential(Credential credential) {
        final String methodPath = "fatcal.credential/";

        URL url = null;
        HttpURLConnection conn = null;
        try {
            Gson gson = new Gson();
            String stringUserJson = gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createDailyreport(Dailyreport dailyreport) {
        final String methodPath = "fatcal.dailyreport/";

        URL url = null;
        HttpURLConnection conn = null;
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String stringUserJson = gson.toJson(dailyreport);
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(stringUserJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringUserJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String totalCalConsumed(int userid, String date){
        final String methodPath = "fatcal.consumption/TotalCalConsumed/" + userid + "/" + date;
        URL url = null;
        HttpURLConnection conn = null;
        String text = new String();

        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                text += inStream.nextLine();
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return text;
    }

    public static String findAllCredential(){
        final String methodPath = "fatcal.credential/";
        URL url = null;
        HttpURLConnection conn = null;
        String text = new String();

        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                text += inStream.nextLine();
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return text;
    }

    public static String findByFoodname(String keyword){
        final String methodPath = "fatcal.food/findByFoodname/" + keyword;
        URL url = null;
        HttpURLConnection conn = null;
        String text = new String();

        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                text += inStream.nextLine();
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return text;
    }

    public static String findByCategory(String category){
        final String methodPath = "fatcal.food/findByCategory/" + category;
        URL url = null;
        HttpURLConnection conn = null;
        String text = new String();

        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                text += inStream.nextLine();
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return text;
    }

    public static String findAllConsumption(){
        final String methodPath = "fatcal.consumption/";
        URL url = null;
        HttpURLConnection conn = null;
        String text = new String();

        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                text += inStream.nextLine();
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return text;
    }

    public static String calculateCalories(int userid){
        final String methodPath = "fatcal.usertable/CalculateCalories/" + userid;
        URL url = null;
        HttpURLConnection conn = null;
        String text = new String();

        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                text += inStream.nextLine();
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return text;
    }

    public static double findCalsBurnPerStep(int userid){
        final String methodPath = "fatcal.usertable/CaloriesBurnPerStep/" + userid;
        URL url = null;
        HttpURLConnection conn = null;
        String text = new String();
        double cal = 0;
        try {
            url = new URL(BASE_URL + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                text += inStream.nextLine();
            }
            JSONArray jsonArray = new JSONArray(text);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            cal = jsonObject.getDouble("cals");


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return cal;
    }

    public static int findBigid(String result){
        int i = 0;
        try {
            JSONArray jsonArray = new JSONArray(result);
            if(jsonArray != null && jsonArray.length() > 2){
                int index = 0;
                int bigger = 0;
                for(index = 0; index < jsonArray.length() - 1; index++){
                    if ( jsonArray.getJSONObject(index).getInt("userid") < jsonArray.getJSONObject(index + 1).getInt("userid")){
                        bigger = jsonArray.getJSONObject(index + 1).getInt("userid");
                    } else{
                        bigger = jsonArray.getJSONObject( index).getInt("userid");
                    }
                }
                i = bigger;
            } else if(jsonArray.length() == 1){
                i = jsonArray.getJSONObject(0).getInt("userid");
            }
            i = i + 1;
        }catch (Exception e){

        }finally {
            return i;
        }
    }

    public static boolean checkUsername(String username){
        boolean checkname = false;
        ArrayList<String> name = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(findAllCredential());
            if(jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++){
                    name.add(jsonArray.getJSONObject(i).getString("username"));
                }
                checkname = checkName(username, name);
            }

        }catch (Exception e){

        }
        return checkname;


    }

    public static boolean checkName(String username, ArrayList<String> name) {
        for(int i = 0; i < name.size(); i++){
            if (username.equals(name.get(i))){
                return true;
            }
        }
        return false;
    }

    public static boolean checkPassword(String user, String password){
        boolean i = true;
        try{
            JSONArray jsonArray = new JSONArray(user);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(jsonObject.getString("password").equals(password)){
                i = true;
            }else{
                i = false;
            }
        } catch(Exception e){

        }
        return i;
    }

    public static String spinnet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            snippet = jsonObject.getString("password");

        }catch (Exception e){ e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    public static String getName(String result){
        String name = null;
        try{
            JSONObject jsonObject  = new JSONObject(result);
            name = jsonObject.getString("userfname");
        }catch(Exception e){}
        return name;
    }

    public static ArrayList<String> getFoodName(String result){
        String name = null;
        ArrayList<String> foodname = new ArrayList<String>();
        try{
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                foodname.add(jsonObject.getString("foodname"));
            }

        }catch(Exception e){}
        return foodname;

    }

    public static int getFoodid(String result){
        int foodid = 0;
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            foodid = jsonObject.getInt("foodid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodid;
    }

    public static int getfoodcalorie(String result){
        int i = 0;
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            i = jsonObject.getInt("calorie");
        }catch (Exception e){}
        return i;
    }

    public static int getfoodfat(String result){
        int i = 0;
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            i = jsonObject.getInt("fat");
        }catch (Exception e){}
        return i;
    }

    public static double getfoodamount(String result){
        double i = 0;
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            i = jsonObject.getInt("servingAmount");
        }catch (Exception e){}
        return i;
    }

    public static String getfoodunit(String result){
        String i = new String();
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            i = jsonObject.getString("servingUnit");
        }catch (Exception e){}
        return i;
    }

    public static int getUserid(String result){
        int i = 0;
        try{
            JSONObject jsonObject = new JSONObject(result);
            i = jsonObject.getInt("userid");
        }catch (Exception e){}
        return i;
    }

    public static String getFirstName(String result){
        String fname = new String();
        try{
            JSONObject jsonObject = new JSONObject(result);
            fname = jsonObject.getString("userfname");

        }catch (Exception e){}

        return fname;

    }

    public static Credential JSONtoCredential(String result){

        Credential credential = new Credential();

        try {
            JSONObject jsonObject = new JSONObject(result);
            String password = jsonObject.getString("password");
            String username = jsonObject.getString("username");
            int userid = jsonObject.getInt("userid");
            credential = new Credential(userid, username, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  credential;


    }

    public static int findBigconsid(){
        String result = findAllConsumption();
        int i = 0;
        try {
            JSONArray jsonArray = new JSONArray(result);
            if(jsonArray != null && jsonArray.length() > 2){
                int index = 0;
                int bigger = 0;
                for(index = 0; index < jsonArray.length() - 1; index++){
                    if ( jsonArray.getJSONObject(index).getInt("consumptionid") < jsonArray.getJSONObject(index + 1).getInt("consumptionid")){
                        bigger = jsonArray.getJSONObject(index + 1).getInt("consumptionid");
                    } else{
                        bigger = jsonArray.getJSONObject( index).getInt("consumptionid");
                    }
                }
                i = bigger;
            } else if(jsonArray.length() == 1){
                i = jsonArray.getJSONObject(0).getInt("consumptionid");
            }
            i = i + 1;
        }catch (Exception e){

        }finally {
            return i;
        }
    }

    public static int findrestcons(String result){
        double cons = 0;
        int consint = 0;
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            cons = jsonObject.getDouble("calories");
            consint = (int)cons;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return consint;

    }






}