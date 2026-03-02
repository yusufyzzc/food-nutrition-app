package com.example.foodnutritionapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.foodnutritionapp.model.*;
import com.example.foodnutritionapp.repository.FoodRepository;
import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    private final FoodRepository repository;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private final LiveData<FoodRepository.Result<NutritionInfo>> searchResults;
    private final LiveData<List<NutritionInfo>> favorites;
    private final LiveData<List<SearchHistory>> recentSearches;
    private final LiveData<Integer> searchCount;
    private final LiveData<List<DailyIntake>> todayIntakes;
    private final LiveData<Double> todayCalories;
    private final LiveData<Double> todayProtein;
    private final LiveData<Double> todayCarbs;
    private final LiveData<Double> todayFat;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);

        searchResults = Transformations.switchMap(searchQuery, query -> {
            if (query == null || query.trim().isEmpty()) {
                return new MutableLiveData<>(FoodRepository.Result.error("Empty query"));
            }
            return repository.searchFood(query);
        });

        favorites = repository.getAllFavorites();
        recentSearches = repository.getRecentSearches();
        searchCount = repository.getSearchCount();
        todayIntakes = repository.getTodayIntakes();
        todayCalories = repository.getTodayCalories();
        todayProtein = repository.getTodayProtein();
        todayCarbs = repository.getTodayCarbs();
        todayFat = repository.getTodayFat();
    }

    // --- Daily Intake Logic ---

    // 1. Simple Quick Add (Main Screen)
    public void addSimpleIntake(String name, double calories) {
        // Create intake with 0 macros since we only know calories
        DailyIntake intake = new DailyIntake(name, calories, 0, 0, 0);
        repository.insertDailyIntake(intake);
    }

    // 2. Detailed Add (Detail Screen)
    public void addWeightedIntake(NutritionInfo food, double value, boolean isGrams) {
        double ratio;

        if (isGrams) {
            // value is grams (e.g., 150g)
            // ratio = 150 / 100 = 1.5
            ratio = value / 100.0;
        } else {
            // value is total Kcal (e.g., 200 kcal)
            // ratio = 200 / 52 (if 52 is kcal per 100g)
            if (food.getCalories() > 0) {
                ratio = value / food.getCalories();
            } else {
                ratio = 1.0; // fallback
            }
        }

        DailyIntake intake = new DailyIntake(
                food.getFoodName(),
                food.getCalories() * ratio,
                food.getProtein() * ratio,
                food.getTotalCarbohydrate() * ratio,
                food.getTotalFat() * ratio
        );
        repository.insertDailyIntake(intake);
    }

    // NEW: Reset today's data and search history
    public void resetTodayData() {
        repository.clearTodayIntake();
        repository.clearSearchHistory();
    }

    // --- Standard Methods ---
    public void searchFood(String query) { searchQuery.setValue(query); }
    public LiveData<FoodRepository.Result<NutritionInfo>> getSearchResults() { return searchResults; }
    public void addToFavorites(NutritionInfo food) { repository.addToFavorites(food); }
    public void removeFromFavorites(NutritionInfo food) { repository.removeFromFavorites(food); }
    public LiveData<List<NutritionInfo>> getFavorites() { return favorites; }
    public LiveData<Boolean> isFavorite(String foodName) { return repository.isFavorite(foodName); }
    public LiveData<List<SearchHistory>> getRecentSearches() { return recentSearches; }
    public LiveData<Integer> getSearchCount() { return searchCount; }
    public void clearSearchHistory() { repository.clearSearchHistory(); }
    public LiveData<List<DailyIntake>> getTodayIntakes() { return todayIntakes; }
    public LiveData<Double> getTodayCalories() { return todayCalories; }
    public LiveData<Double> getTodayProtein() { return todayProtein; }
    public LiveData<Double> getTodayCarbs() { return todayCarbs; }
    public LiveData<Double> getTodayFat() { return todayFat; }
    public void removeFromDailyIntake(DailyIntake intake) { repository.removeFromDailyIntake(intake); }
}