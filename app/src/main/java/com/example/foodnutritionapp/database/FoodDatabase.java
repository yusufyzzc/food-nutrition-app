package com.example.foodnutritionapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.foodnutritionapp.model.NutritionInfo;
import com.example.foodnutritionapp.model.SearchHistory;
import com.example.foodnutritionapp.model.DailyIntake;

@Database(
        entities = {
                NutritionInfo.class,
                SearchHistory.class,
                DailyIntake.class
        },
        version = 2,  // Version artırıldı
        exportSchema = false
)
public abstract class FoodDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();
    public abstract SearchHistoryDao searchHistoryDao();
    public abstract DailyIntakeDao dailyIntakeDao();

    private static volatile FoodDatabase INSTANCE;

    public static FoodDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FoodDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    FoodDatabase.class,
                                    "food_database"
                            )
                            .fallbackToDestructiveMigration() // Geliştirme için
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}