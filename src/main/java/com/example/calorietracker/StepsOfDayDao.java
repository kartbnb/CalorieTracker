package com.example.calorietracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsOfDayDao {
    @Query("SELECT * FROM stepsofday")
    List<StepsOfDay> getAll();

    @Query("SELECT * FROM stepsofday WHERE id = :stepid")
    StepsOfDay findById(int stepid);

    @Query("SELECT * FROM stepsofday WHERE userid = :userid")
    List<StepsOfDay> findByUserid(int userid);

    @Insert
    void insertAll(StepsOfDay... stepsOfDays);

    @Insert
    long insert(StepsOfDay stepsOfDay);

    @Delete
    void delete(StepsOfDay stepsOfDay);

    @Update(onConflict = REPLACE)
    public void updateStepsOfDay(StepsOfDay... stepsOfDays);

    @Query("DELETE FROM stepsofday")
    void deleteAll();
}
