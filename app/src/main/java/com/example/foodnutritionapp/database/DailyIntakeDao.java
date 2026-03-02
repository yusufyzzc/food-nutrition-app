package com.example.foodnutritionapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.foodnutritionapp.model.DailyIntake;
import java.util.List;

@Dao
public interface DailyIntakeDao {
    @Insert
    void insert(DailyIntake dailyIntake);

    @Delete
    void delete(DailyIntake dailyIntake);

    @Query("SELECT * FROM daily_intake WHERE date = :date ORDER BY timestamp DESC")
    LiveData<List<DailyIntake>> getTodayIntakes(String date);

    @Query("SELECT SUM(calories) FROM daily_intake WHERE date = :date")
    LiveData<Double> getTodayCalories(String date);

    @Query("SELECT SUM(protein) FROM daily_intake WHERE date = :date")
    LiveData<Double> getTodayProtein(String date);

    @Query("SELECT SUM(carbs) FROM daily_intake WHERE date = :date")
    LiveData<Double> getTodayCarbs(String date);

    @Query("SELECT SUM(fat) FROM daily_intake WHERE date = :date")
    LiveData<Double> getTodayFat(String date);

    @Query("DELETE FROM daily_intake WHERE date = :date")
    void clearDay(String date);

    @Query("SELECT * FROM daily_intake ORDER BY date DESC, timestamp DESC LIMIT 100")
    LiveData<List<DailyIntake>> getAllIntakes();

    // ✅ YENİ: 30 günden eski verileri sil
    @Query("DELETE FROM daily_intake WHERE date < :cutoffDate")
    void deleteOlderThan(String cutoffDate);
}