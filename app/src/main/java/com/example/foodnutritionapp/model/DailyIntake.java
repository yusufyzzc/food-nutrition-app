package com.example.foodnutritionapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "daily_intake")
public class DailyIntake {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String date;

    @NonNull
    private String foodName;

    private double calories;
    private double protein;
    private double carbs;
    private double fat;

    private long timestamp;

    public DailyIntake(@NonNull String foodName, double calories,
                       double protein, double carbs, double fat) {
        this.date = getCurrentDate();
        this.foodName = foodName;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.timestamp = System.currentTimeMillis();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(new Date());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    @NonNull public String getDate() { return date; }
    public void setDate(@NonNull String date) { this.date = date; }
    @NonNull public String getFoodName() { return foodName; }
    public void setFoodName(@NonNull String foodName) { this.foodName = foodName; }
    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }
    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }
    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }
    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}