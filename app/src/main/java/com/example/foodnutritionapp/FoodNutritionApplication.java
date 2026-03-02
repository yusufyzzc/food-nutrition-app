package com.example.foodnutritionapp;

import android.app.Application;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.example.foodnutritionapp.worker.DailyCleanupWorker;
import java.util.concurrent.TimeUnit;

/**
 * Custom Application class for app-wide initialization
 * Sets up WorkManager for background tasks
 */
public class FoodNutritionApplication extends Application {
    private static final String CLEANUP_WORK_NAME = "daily_cleanup_work";

    @Override
    public void onCreate() {
        super.onCreate();

        // Schedule periodic cleanup work
        scheduleCleanupWork();
    }

    private void scheduleCleanupWork() {
        // Define constraints - only run when device is idle and charging
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiresDeviceIdle(true)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build();

        // Create periodic work request (runs once per day)
        PeriodicWorkRequest cleanupWork = new PeriodicWorkRequest.Builder(
                DailyCleanupWorker.class,
                1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .addTag("cleanup")
                .build();

        // Enqueue work with unique name (keeps existing work if already scheduled)
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                CLEANUP_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                cleanupWork
        );
    }
}