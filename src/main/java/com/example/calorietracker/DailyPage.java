package com.example.calorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DailyPage extends Fragment {
    View vDailyPage;
    Spinner category;
    Spinner food;
    List<String> foodbelow;
    String image;
    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> foodAdapter;
    String foodcalorie;
    TextView foodresult;
    ImageView foodimage;
    String selectcategory;
    Button applyfood;
    Button newfood;
    int foodid;
    int userid;
    SharedPreferences sharedPreferences;
    EditText foodcons;
    String datetoday;
    int totalconsumed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vDailyPage = inflater.inflate(R.layout.daily_page, container, false);
        category = vDailyPage.findViewById(R.id.food_category);
        food = vDailyPage.findViewById(R.id.food);
        foodresult = vDailyPage.findViewById(R.id.fooddetail);
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = sharedPreferences.getInt("userid",0);
        datetoday = sharedPreferences.getString("date" + userid,null);
        foodcons = vDailyPage.findViewById(R.id.quantitycons);
        List<String> foodcategory = new ArrayList<String>();
        foodcategory.add("Drink");
        foodcategory.add("Meal");
        foodcategory.add("Meat");
        foodcategory.add("Snack");
        foodcategory.add("Bread");
        foodcategory.add("Cake");
        foodcategory.add("Fruit");
        foodcategory.add("Vegies");
        foodcategory.add("Other");
        categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, foodcategory);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);
        foodimage = vDailyPage.findViewById(R.id.foodimage);
        applyfood = vDailyPage.findViewById(R.id.add_food);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FindByCategory findByCategory = new FindByCategory();
                selectcategory = category.getItemAtPosition(position).toString();
                findByCategory.execute(selectcategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        food.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SearchFood searchFood = new SearchFood();
                searchFood.execute(food.getItemAtPosition(position).toString(), selectcategory);

                while (true) {
                    try {
                        foodimage.setImageBitmap(searchFood.get());
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        applyfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFoodConsume postFoodConsume = new PostFoodConsume();
                postFoodConsume.execute();
            }
        });



        return vDailyPage;

    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++ ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private class FindByCategory extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            foodbelow = RestClient.getFoodName(RestClient.findByCategory(params[0]));
            return foodbelow;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            foodAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, foodbelow);
            foodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            food.setAdapter(foodAdapter);

        }
    }

    private class SearchFood extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String search = GoogleSearchAPI.search(params[0] + "+" + params[1], new String[]{"num"}, new String[]{"1"});
            image = GoogleSearchAPI.getImage(search);
            String fooddetail = RestClient.findByFoodname(params[0]);
            foodid = RestClient.getFoodid(fooddetail);

            Bitmap bmp = null;

            try {
                InputStream in = new java.net.URL(image).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            foodcalorie = "Foodname: " + params[0] + "\ncalorie: " + RestClient.getfoodcalorie(fooddetail) + "\nfat: " + RestClient.getfoodfat(fooddetail) + "\nServingAmount: " + RestClient.getfoodamount(fooddetail) + "\nServingUnit: " + RestClient.getfoodunit(fooddetail);

            return bmp;

        }

        protected void onPostExecute(Bitmap result) {
            //foodimage.setImageBitmap(result);
            //
            foodresult.setText(foodcalorie);
        }

    }

    private class PostFoodConsume extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void...params){
            Credential credential = new Credential(userid);
            Food foodeat = new Food(foodid);
            Date date = Calendar.getInstance().getTime();
            if(isNumeric(foodcons.getText().toString())) {
                int quantity = Integer.parseInt(foodcons.getText().toString());
                Consumption consumption = new Consumption(RestClient.findBigconsid(), credential, date, foodeat, quantity);
                RestClient.createConsumption(consumption);
                String cons = RestClient.totalCalConsumed(userid ,datetoday);
                try {
                    JSONArray jsonArray = new JSONArray(cons);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    totalconsumed = jsonObject.getInt("totalcal");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("calorieconsumed" + userid, totalconsumed);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;

            }else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(!result){
                Toast.makeText(getActivity(), "Please enter a valid quantity", Toast.LENGTH_SHORT).show();
            }else{


            }
        }

    }




}
