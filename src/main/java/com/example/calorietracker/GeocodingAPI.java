package com.example.calorietracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GeocodingAPI {
    private static final String API_KEY = "AIzaSyChFRyAzibNXhi_0KNP_CQWQY6O66ZcwqY";

   // https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyChFRyAzibNXhi_0KNP_CQWQY6O66ZcwqY
    private static final String SEARCH_ID_cx = "004898564507443696836:hfyj1pgd7aq";
    public static String search(String keyword, String[] params, String[] values) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";
        if (params!=null && values!=null){
            for (int i =0; i < params.length; i ++){
                query_parameter += "&";
                query_parameter += params[i];
                query_parameter += "=";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+
                    API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){ e.printStackTrace();
        }finally{ connection.disconnect();
        }
        return textResult;
    }

    public static String getImage(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            JSONObject jsonObject2 = jsonObject1.getJSONObject("pagemap");
            JSONArray jsonArray1 = jsonObject2.getJSONArray("metatags");
            if(jsonArray1 != null && jsonArray1.length() > 0) {
                snippet = jsonArray1.getJSONObject(0).getString("og:image");
            }
        }catch (Exception e){ e.printStackTrace();
            snippet = "NO Image found";
        }
        return snippet;
    }
}
