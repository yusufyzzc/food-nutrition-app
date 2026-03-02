package com.example.foodnutritionapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.foodnutritionapp.model.NutritionInfo;
import java.util.List;

@Dao
public interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NutritionInfo food);

    @Delete
    void delete(NutritionInfo food);

    @Query("SELECT * FROM favorites ORDER BY foodName ASC")
    LiveData<List<NutritionInfo>> getAllFavorites();

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE foodName = :foodName LIMIT 1)")
    LiveData<Boolean> isFavorite(String foodName);
}
