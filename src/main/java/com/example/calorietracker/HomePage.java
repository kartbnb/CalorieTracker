package com.example.calorietracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends Fragment {
    View vMain;
    private TextView welcome;
    private TextView goal;
    private Button setCalories;
    private EditText goaledittext;
    SharedPreferences spMyUnits;
    List<StepsOfDay> steps;
    StepsOfDayDatabase db = null;
    int userid;
    int totalsteps;
    int totalconsumed;
    String datetoday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        vMain = inflater.inflate(R.layout.home_page_main, container, false);
        Bundle arguments = this.getArguments();
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                StepsOfDayDatabase.class,
                "StepsOfDayDatabase1")
                .fallbackToDestructiveMigration()
                .build();

        spMyUnits = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);


        Map<String, ?> allEntries = spMyUnits.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }



        steps = new ArrayList<StepsOfDay>();
        userid = spMyUnits.getInt("userid", 0);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datetoday = dateFormat.format(date);
        GetTotalSteps getTotalSteps = new GetTotalSteps();
        getTotalSteps.execute();
        Findcalperstep findcalperstep = new Findcalperstep();
        findcalperstep.execute(userid);
        TotalCalCons totalCalCons = new TotalCalCons();
        totalCalCons.execute();
        CalculateCalories calculateCalories = new CalculateCalories();
        calculateCalories.execute();
        String fname = spMyUnits.getString("fname", null);
        final String caloriegoal = spMyUnits.getString("caloriegoal" + userid, "Set Goal for today");
        welcome = vMain.findViewById(R.id.welcome);
        welcome.setText("Welcome to Calorie tracker " + fname + "!");
        goal = vMain.findViewById(R.id.caloriegoal);
        goal.setText(caloriegoal);
        goaledittext = vMain.findViewById(R.id.goaledit);
        setCalories = vMain.findViewById(R.id.setcaloriegoalbtn);
        setCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = spMyUnits.edit();
                String calorieset = goaledittext.getText().toString();
                if(isNumeric(calorieset)) {
                    editor.putString("caloriegoal" + userid, calorieset);
                    editor.apply();
                    goal.setText(calorieset);
                }else{
                    Toast.makeText(getActivity(),"Please enter a number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vMain;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++ ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    private class GetTotalSteps extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
//            Date date = Calendar.getInstance().getTime();
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            String datestr = dateFormat.format(date);
            SharedPreferences.Editor editor = spMyUnits.edit();
            steps = db.stepsOfDayDao().findByUserid(userid);
            if (!(steps.isEmpty() || steps == null)) {

                for (int i = 0; i < steps.size(); i++) {
                    StepsOfDay temp = steps.get(i);
                    totalsteps = totalsteps + temp.getStepsofperiod();
                }
            }else{
                totalsteps = 0;
            }
            editor.putInt("totalsteps" + userid, totalsteps);
            //editor.putString("date", datestr);
            editor.apply();
            return totalsteps;
        }

    }

    private class Findcalperstep extends AsyncTask<Integer, Void, Double>{

        @Override
        protected Double doInBackground(Integer...params){
            double calps = 0.0;
            calps = RestClient.findCalsBurnPerStep(params[0]);
            return calps;
        }

        protected  void onPostExecute(Double result){
            String burnperstep = Double.toString(result);
            SharedPreferences.Editor editor = spMyUnits.edit();
            editor.putString("bps" + userid, burnperstep);
            editor.apply();
        }
    }

    private class TotalCalCons extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            String cons = RestClient.totalCalConsumed(userid ,datetoday);
            try {
                JSONArray jsonArray = new JSONArray(cons);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                totalconsumed = jsonObject.getInt("totalcal");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return totalconsumed;
        }


        protected void onPostExecute(Integer result) {
            SharedPreferences.Editor editor = spMyUnits.edit();
            editor.putInt("calorieconsumed" + userid, result);
            editor.putString("date" + userid, datetoday);
            editor.apply();
        }
    }

    private class CalculateCalories extends AsyncTask<Integer, Void, Integer>{
        @Override
        protected Integer doInBackground(Integer...params){
            String restconsstr = RestClient.calculateCalories(userid);
            int restcons = RestClient.findrestcons(restconsstr);
            return restcons;

        }

        @Override
        protected void onPostExecute(Integer result){
            SharedPreferences.Editor editor = spMyUnits.edit();
            editor.putInt("calorierest" + userid, result);
            editor.apply();
        }
    }










    }
