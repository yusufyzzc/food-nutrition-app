package com.example.foodnutritionapp.worker;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.foodnutritionapp.database.FoodDatabase;
import com.example.foodnutritionapp.database.DailyIntakeDao;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * WorkManager Worker for cleaning up old daily intake data
 * Runs periodically (e.g., daily at midnight) to remove old data
 *
 * This demonstrates Background Work requirement for the course
 */
public class DailyCleanupWorker extends Worker {
    private static final String TAG = "DailyCleanupWorker";
    private static final int DAYS_TO_KEEP = 30; // Keep last 30 days of data

    public DailyCleanupWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Starting daily cleanup work");

        try {
            // Get database instance
            FoodDatabase database = FoodDatabase.getInstance(getApplicationContext());
            DailyIntakeDao dailyIntakeDao = database.dailyIntakeDao();

            // Calculate cutoff date (30 days ago)
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -DAYS_TO_KEEP);
            Date cutoffDate = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String cutoffDateStr = sdf.format(cutoffDate);


            dailyIntakeDao.deleteOlderThan(cutoffDateStr);

            Log.d(TAG, "Cleanup completed successfully. Removed data older than: " + cutoffDateStr);

            return Result.success();

        } catch (Exception e) {
            Log.e(TAG, "Error during cleanup: " + e.getMessage(), e);
            return Result.failure();
        }
    }
}