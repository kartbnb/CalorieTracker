package com.example.calorietracker;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NDBAPI {

    private static final String API_KEY = "4fnsOtbz7hmcmcDwFznNHu7Gas3ldPVGzJMtCMXa";
    public static String search(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        try {
            url = new URL("https://api.nal.usda.gov/ndb/search/?format=json&q=" + keyword + "&sort=n&max=25&offset=0&api_key=" + API_KEY);
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
}
