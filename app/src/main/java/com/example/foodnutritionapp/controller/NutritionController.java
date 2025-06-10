package com.example.foodnutritionapp.controller;

import android.content.Context;
import com.example.foodnutritionapp.model.NutritionResponse;
import com.example.foodnutritionapp.model.SearchRequest;
import com.example.foodnutritionapp.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Controller class managing nutrition data operations
 * Follows MVC pattern and Single Responsibility Principle
 */
public class NutritionController {
    private final NetworkManager networkManager;
    private NutritionDataListener dataListener;

    public interface NutritionDataListener {
        void onDataLoaded(NutritionResponse response);
        void onDataError(String error);
        void onNetworkError();
    }

    public NutritionController(Context context) {
        this.networkManager = NetworkManager.getInstance(context);
    }

    public void setDataListener(NutritionDataListener listener) {
        this.dataListener = listener;
    }

    /**
     * Search for nutrition information
     * @param query Food name to search
     */
    public void searchNutritionInfo(String query) {
        // Validate input
        if (query == null || query.trim().isEmpty()) {
            if (dataListener != null) {
                dataListener.onDataError("Please enter a food name");
            }
            return;
        }

        // Check network connectivity
        if (!networkManager.isNetworkAvailable()) {
            if (dataListener != null) {
                dataListener.onNetworkError();
            }
            return;
        }

        SearchRequest request = new SearchRequest(query.trim());
        Call<NutritionResponse> call = networkManager.getApi().getNutritionInfo(request);

        call.enqueue(new Callback<NutritionResponse>() {
            @Override
            public void onResponse(Call<NutritionResponse> call, Response<NutritionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NutritionResponse nutritionResponse = response.body();
                    if (nutritionResponse.getFoods() != null && !nutritionResponse.getFoods().isEmpty()) {
                        if (dataListener != null) {
                            dataListener.onDataLoaded(nutritionResponse);
                        }
                    } else {
                        if (dataListener != null) {
                            dataListener.onDataError("No nutrition information found for this food");
                        }
                    }
                } else {
                    if (dataListener != null) {
                        dataListener.onDataError("Failed to get nutrition information. Please try again.");
                    }
                }
            }

            @Override
            public void onFailure(Call<NutritionResponse> call, Throwable t) {
                if (dataListener != null) {
                    dataListener.onDataError("Network error: " + t.getMessage());
                }
            }
        });
    }
}