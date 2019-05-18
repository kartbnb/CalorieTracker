package com.example.calorietracker;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StepsOfDay.class}, version = 2, exportSchema = false)
public abstract class StepsOfDayDatabase extends RoomDatabase {

    public abstract StepsOfDayDao stepsOfDayDao();

    private static volatile StepsOfDayDatabase INSTANCE;
    static StepsOfDayDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StepsOfDayDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StepsOfDayDatabase.class, "stepsofday_database").build();
                }
            }
        }
        return INSTANCE; }
}