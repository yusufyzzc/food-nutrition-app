package com.example.foodnutritionapp.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.foodnutritionapp.database.*;
import com.example.foodnutritionapp.model.*;
import com.example.foodnutritionapp.network.NetworkManager;
import com.example.foodnutritionapp.network.UsdaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FoodRepository {
    private final UsdaAPI api;
    private final FoodDao foodDao;
    private final SearchHistoryDao searchHistoryDao;
    private final DailyIntakeDao dailyIntakeDao;
    private final Executor executor;
    private final NetworkManager networkManager;

    public FoodRepository(Context context) {
        this.networkManager = NetworkManager.getInstance(context);
        this.api = networkManager.getUsdaApi();
        FoodDatabase database = FoodDatabase.getInstance(context);
        this.foodDao = database.foodDao();
        this.searchHistoryDao = database.searchHistoryDao();
        this.dailyIntakeDao = database.dailyIntakeDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    // ==================== SEARCH ====================
    public LiveData<Result<NutritionInfo>> searchFood(String query) {
        MutableLiveData<Result<NutritionInfo>> result = new MutableLiveData<>();
        if (!networkManager.isNetworkAvailable()) {
            result.setValue(Result.error("No internet connection"));
            return result;
        }
        result.setValue(Result.loading());
        saveSearchHistory(query);

        api.searchFood(UsdaAPI.API_KEY, query, 1).enqueue(new Callback<UsdaResponse>() {
            @Override
            public void onResponse(Call<UsdaResponse> call, Response<UsdaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UsdaResponse usdaResponse = response.body();
                    if (usdaResponse.getFoods() != null && !usdaResponse.getFoods().isEmpty()) {
                        NutritionInfo info = usdaResponse.getFoods().get(0).toNutritionInfo();
                        result.setValue(Result.success(info));
                    } else {
                        result.setValue(Result.error("Food not found"));
                    }
                } else {
                    result.setValue(Result.error("Server error"));
                }
            }
            @Override
            public void onFailure(Call<UsdaResponse> call, Throwable t) {
                result.setValue(Result.error("Network error: " + t.getMessage()));
            }
        });
        return result;
    }

    // ==================== FAVORITES ====================
    public void addToFavorites(NutritionInfo food) { executor.execute(() -> foodDao.insert(food)); }
    public void removeFromFavorites(NutritionInfo food) { executor.execute(() -> foodDao.delete(food)); }
    public LiveData<List<NutritionInfo>> getAllFavorites() { return foodDao.getAllFavorites(); }
    public LiveData<Boolean> isFavorite(String foodName) { return foodDao.isFavorite(foodName); }

    // ==================== HISTORY ====================
    public void saveSearchHistory(String query) {
        executor.execute(() -> {
            SearchHistory history = new SearchHistory(query);
            searchHistoryDao.insert(history);
        });
    }
    public LiveData<List<SearchHistory>> getRecentSearches() { return searchHistoryDao.getRecentSearches(); }
    public LiveData<Integer> getSearchCount() { return searchHistoryDao.getSearchCount(); }
    public void clearSearchHistory() { executor.execute(() -> searchHistoryDao.clearAll()); }

    // ==================== DAILY INTAKE ====================

    // Generic Insert for Manual or Detailed Entry
    public void insertDailyIntake(DailyIntake intake) {
        executor.execute(() -> dailyIntakeDao.insert(intake));
    }

    // Legacy support method if needed
    public void addToDailyIntake(NutritionInfo food) {
        executor.execute(() -> {
            DailyIntake intake = new DailyIntake(
                    food.getFoodName(), food.getCalories(), food.getProtein(),
                    food.getTotalCarbohydrate(), food.getTotalFat());
            dailyIntakeDao.insert(intake);
        });
    }

    public LiveData<List<DailyIntake>> getTodayIntakes() { return dailyIntakeDao.getTodayIntakes(getCurrentDate()); }
    public LiveData<Double> getTodayCalories() { return dailyIntakeDao.getTodayCalories(getCurrentDate()); }
    public LiveData<Double> getTodayProtein() { return dailyIntakeDao.getTodayProtein(getCurrentDate()); }
    public LiveData<Double> getTodayCarbs() { return dailyIntakeDao.getTodayCarbs(getCurrentDate()); }
    public LiveData<Double> getTodayFat() { return dailyIntakeDao.getTodayFat(getCurrentDate()); }
    public void removeFromDailyIntake(DailyIntake intake) { executor.execute(() -> dailyIntakeDao.delete(intake)); }

    // NEW: Clear today's intake data
    public void clearTodayIntake() {
        executor.execute(() -> dailyIntakeDao.clearDay(getCurrentDate()));
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
    }

    public static class Result<T> {
        public enum Status { SUCCESS, ERROR, LOADING }
        private final Status status;
        private final T data;
        private final String message;
        private Result(Status status, T data, String message) {
            this.status = status;
            this.data = data;
            this.message = message;
        }
        public static <T> Result<T> success(T data) { return new Result<>(Status.SUCCESS, data, null); }
        public static <T> Result<T> error(String message) { return new Result<>(Status.ERROR, null, message); }
        public static <T> Result<T> loading() { return new Result<>(Status.LOADING, null, null); }
        public Status getStatus() { return status; }
        public T getData() { return data; }
        public String getMessage() { return message; }
    }
}