package com.example.calorietracker;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Intent intent;
    private AlarmManager alarmMgr;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(this, TimeService.class);
        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60 * 1000, pendingIntent);
        intent = getIntent();
        Bundle bundle=intent.getExtras();
        //userid = bundle.getInt("userid");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(Home.this, TimeService.class);
        pendingIntent = PendingIntent.getService(Home.this, 0, alarmIntent, 0);
        long currenttime = System.currentTimeMillis();
        long oneday = 24 * 60 * 60 * 1000;
        currenttime = currenttime + oneday;
        long minustime = currenttime % oneday;
        long settime = currenttime - minustime - (60 * 1000);
        alarmMgr.setRepeating(AlarmManager.RTC, settime, 24 * 60 * 60 * 1000, pendingIntent);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Calories Tracker");
        FragmentManager fragmentManager = getFragmentManager();
        Fragment newpage = new HomePage();
        newpage.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, newpage).commit();
        //fragmentManager.beg


    }



    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFragment = null;
        switch(id){
            case R.id.nav_home:
                nextFragment = new HomePage();
                break;
            case R.id.nav_calorie:
                nextFragment = new CaloriesPage();
                break;
            case R.id.nav_steps:
                nextFragment = new StepsPage();
                break;
            case R.id.nav_daily:
                nextFragment = new DailyPage();
                break;
            case R.id.nav_map:
                nextFragment = new MapsPage();
                break;
            case R.id.nav_report:
                nextFragment = new ReportPage();
                break;

        }
        intent = getIntent();
        Bundle userid = intent.getExtras();
        FragmentManager fragmentManager = getFragmentManager();
        nextFragment.setArguments(userid);
        fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
