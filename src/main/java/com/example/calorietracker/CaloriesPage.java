package com.example.calorietracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CaloriesPage extends Fragment {
    View vCaloriesPage;
    TextView caloriegoal;
    TextView totalsteps;
    TextView calorieconstv;
    TextView burnedcalorie;
    int userid;
    SharedPreferences sharedPreferences;
    //long calburned;
    int calcons;
    int stepsget;
    Date date;
    Button post;
    String bpsstr;
    double bps;
    int restburn;
    long stepburn;
    long totalburn;

    StepsOfDayDatabase db = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vCaloriesPage = inflater.inflate(R.layout.calorie_page, container, false);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                StepsOfDayDatabase.class,
                "StepsOfDayDatabase1")
                .fallbackToDestructiveMigration()
                .build();
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = sharedPreferences.getInt("userid", 0);
        String calgoal = sharedPreferences.getString("caloriegoal" + userid, "Please set goal");

        post = vCaloriesPage.findViewById(R.id.post);
        caloriegoal = vCaloriesPage.findViewById(R.id.set_goal);
        caloriegoal.setText(calgoal);

        totalsteps = vCaloriesPage.findViewById(R.id.steps_per_day);
        stepsget = sharedPreferences.getInt("totalsteps" + userid, 0);
        totalsteps.setText(stepsget + "");

        bpsstr = sharedPreferences.getString("bps" + userid,"0");
        bps = Double.parseDouble(bpsstr);
        restburn = sharedPreferences.getInt("calorierest" + userid,0);
        stepburn = Math.round(stepsget * bps);
        totalburn = restburn + stepburn;
        Log.i("wcnm",String.valueOf(totalburn));



        burnedcalorie = vCaloriesPage.findViewById(R.id.total_calories_1);
//        calburned = sharedPreferences.getLong("calorie burned", 0);
        burnedcalorie.setText(totalburn + " ");

        calorieconstv = vCaloriesPage.findViewById(R.id.consumedcalorie);
        calcons = sharedPreferences.getInt("calorieconsumed" + userid, 0);
        calorieconstv.setText(calcons + "");
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostReport postReport = new PostReport();
                postReport.execute();
            }
        });


        return vCaloriesPage;
    }

    private class PostReport extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean posts;
            try {
                //Date currentTime = Calendar.getInstance().getTime();
                //String strTime = currentTime.toString();
                //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "user", Context.MODE_PRIVATE);
                //steps = sharedPreferences.getInt("totalsteps", 0);
                //userid = sharedPreferences.getInt("userid", 0);
                String datestr = sharedPreferences.getString("date" + userid, null);
                String goalstr = sharedPreferences.getString("caloriegoal" + userid, "zero");
                int goal = 0;
                if (isNumeric(goalstr)) {
                    goal = Integer.parseInt(goalstr);
                } else {
                    goal = 0;
                }
                //calcons = sharedPreferences.getInt("calorieconsumed", 0);
                //burn = sharedPreferences.getLong("calorie burned", 0);
                Usertable user = new Usertable(userid);

                date = new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
                DailyreportPK report = new DailyreportPK(date, userid);


                int i = (int) totalburn;
                Dailyreport dailyreport = new Dailyreport(user, report, calcons, i, stepsget, goal);
                RestClient.createDailyreport(dailyreport);
                //TimeService.DeleteDatabase deleteDatabase = new TimeService.DeleteDatabase();
                //deleteDatabase.execute();
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
                posts = true;
            } catch (ParseException e) {
                e.printStackTrace();
                posts = false;
            }
            return posts;
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                Toast.makeText(getActivity(), "Posted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Post Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.stepsOfDayDao().deleteAll();
            return null;
        }
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }




}
