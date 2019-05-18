package com.example.calorietracker;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StepsPage extends Fragment {
    View vStepspage;
    StepsOfDayDatabase db = null;
    EditText stepstaken;
    TextView stepsrecord;
    Button btnsteps;
    List<HashMap<String, String>> stepsListArray;
    SimpleAdapter myListAdapter;
    ListView stepsList;
    String[] colHEAD;
    int[] dataCell;
    Button btndelete;
    SharedPreferences sharedPreferences;
    private AlarmManager alarmMgr;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;
    List<StepsOfDay> steps;
    int userid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vStepspage = inflater.inflate(R.layout.steps_page, container, false);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                StepsOfDayDatabase.class,
                "StepsOfDayDatabase1")
                .fallbackToDestructiveMigration()
                .build();


        stepstaken = vStepspage.findViewById(R.id.steps_take);
        btnsteps = vStepspage.findViewById(R.id.enter_steps);
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = sharedPreferences.getInt("userid", 0);
       // bpsstr = sharedPreferences.getString("bps", null);
       // bps = Double.parseDouble(bpsstr);


        btnsteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase insertDatabase = new InsertDatabase();
                insertDatabase.execute();
            }
        });
        colHEAD = new String[]{"Time", "Steps taken"};
        dataCell = new int[]{R.id.timeofstep, R.id.noofstep};
        stepsList = vStepspage.findViewById(R.id.stepsshow);

        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();
        stepsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changesteps(position);
            }


        });

        btndelete = vStepspage.findViewById(R.id.deleteall);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });
        return vStepspage;
    }

    private class InsertDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            if (!(stepstaken.getText().toString().isEmpty())) {
                String details = stepstaken.getText().toString();
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String datestr = dateFormat.format(currentTime);
                StepsOfDay stepsOfDay = new StepsOfDay(userid, Integer.parseInt(details), datestr);
                long id = db.stepsOfDayDao().insert(stepsOfDay);
                return (id + " " + Integer.parseInt(details) + " has been added");
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            ReadDatabase readDatabase = new ReadDatabase();
            readDatabase.execute();
        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            steps = db.stepsOfDayDao().findByUserid(userid);
            stepsListArray = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> title = new HashMap<String, String>();
//            title.put("Time", "Time");
//            title.put("Steps taken", "Steps taken");
 //           stepsListArray.add(title);
            if (!(steps.isEmpty() || steps == null)) {
                String allSteps = "";
                int totalsteps = 0;
                for (int i = 0; i < steps.size(); i++) {
                    StepsOfDay temp = steps.get(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    totalsteps = totalsteps + temp.getStepsofperiod();
                    map.put("Time", temp.getTime());
                    map.put("Steps taken", Integer.toString(temp.getStepsofperiod()));
                    stepsListArray.add(map);
                }

//                Date date = Calendar.getInstance().getTime();
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//                String datestr = dateFormat.format(date);
                editor.putInt("totalsteps" + userid, totalsteps);
                //editor.putString("date", datestr);
                editor.apply();
                return true;
            }
            else{
                return false;

            }
        }

        protected void onPostExecute(Boolean result){
            if(result) {
                myListAdapter = new SimpleAdapter(getActivity(), stepsListArray, R.layout.list_view, colHEAD, dataCell);
                stepsList.setAdapter(myListAdapter);
            }else{

            }
        }


    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.stepsOfDayDao().deleteAll();
            return null;
        }

        protected void onPostExecute(Void param) {

        }
    }

    private class UpdateDatabase extends AsyncTask<StepsOfDay, Void, Void> {
        @Override
        protected Void doInBackground(StepsOfDay... params) {
            db.stepsOfDayDao().updateStepsOfDay(params[0]);
            ReadDatabase readDatabase = new ReadDatabase();
            readDatabase.execute();
            return null;
        }
    }

    private void changesteps(final int position1) {
        AlertDialog.Builder popup = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.dialog, null);
        popup.setView(view);
        popup.setCancelable(true);
        final EditText input_edt = (EditText) view.findViewById(R.id.stepschange);

        popup.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StepsOfDay temp = steps.get(position1);
                temp.setStepsofperiod(Integer.parseInt(input_edt.getText().toString()));
                UpdateDatabase updateDatabase = new UpdateDatabase();
                updateDatabase.execute(temp);
            }
        });
        popup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        popup.show();
    }
}




