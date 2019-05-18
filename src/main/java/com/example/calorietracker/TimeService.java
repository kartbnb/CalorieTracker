package com.example.calorietracker;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeService extends IntentService {
    StepsOfDayDatabase db = null;
    int steps;
    int userid;
    String datestr;
    long burn;
    int cons;
    String goalstr;
    int goal;
    Date date;


    public TimeService() {
        super("ScheduledIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        db = Room.databaseBuilder(getApplicationContext(),
                StepsOfDayDatabase.class,
                "StepsOfDayDatabase1")
                .fallbackToDestructiveMigration()
                .build();
        PostReport postReport = new PostReport();
        postReport.execute();

        //Log.i("message ", "The number of runs: " + counter + " times" + " " + strTime);
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.stepsOfDayDao().deleteAll();
            return null;
        }
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++ ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private class PostReport extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void...params){
            Date currentTime = Calendar.getInstance().getTime();
            String strTime=currentTime.toString();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "user", Context.MODE_PRIVATE);
            userid = sharedPreferences.getInt("userid", 0);
            steps = sharedPreferences.getInt("totalsteps" + userid, 0);
            datestr = sharedPreferences.getString("date" + userid, null);
            goalstr = sharedPreferences.getString("caloriegoal" + userid, null);
            if(isNumeric(goalstr)){
                goal = Integer.parseInt(goalstr);
            }else{
                goal = 0;
            }
            cons = sharedPreferences.getInt("calorieconsumed" + userid, 0);
            burn = sharedPreferences.getLong("calorie burned" + userid, 0);
            Usertable user = new Usertable(userid);
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(datestr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DailyreportPK report = new DailyreportPK(date, userid);
            int i = (int)burn;
            Dailyreport dailyreport = new Dailyreport(user, report, cons, i, steps, goal);
            RestClient.createDailyreport(dailyreport);
            DeleteDatabase deleteDatabase = new DeleteDatabase();
            deleteDatabase.execute();
            return null;
        }
    }

}