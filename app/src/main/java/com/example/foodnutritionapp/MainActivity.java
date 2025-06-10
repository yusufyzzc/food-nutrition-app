package com.example.foodnutritionapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodnutritionapp.controller.InputValidator;
import com.example.foodnutritionapp.controller.NutritionController;
import com.example.foodnutritionapp.model.NutritionInfo;
import com.example.foodnutritionapp.model.NutritionResponse;
import com.example.foodnutritionapp.view.NutritionDetailActivity;

/**
 * Main Activity - Entry point of the application
 * Follows MVC pattern as View component
 */
public class MainActivity extends AppCompatActivity implements NutritionController.NutritionDataListener {

    private EditText editTextFoodName;
    private Button buttonSearch;
    private ProgressBar progressBar;
    private NutritionController nutritionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeController();
        setupEventListeners();
    }

    /**
     * Initialize UI components
     */
    private void initializeViews() {
        editTextFoodName = findViewById(R.id.editTextFoodName);
        buttonSearch = findViewById(R.id.buttonSearch);
        progressBar = findViewById(R.id.progressBar);

        // Initially hide progress bar
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Initialize controller and set data listener
     */
    private void initializeController() {
        nutritionController = new NutritionController(this);
        nutritionController.setDataListener(this);
    }

    /**
     * Setup event listeners for UI components
     */
    private void setupEventListeners() {
        buttonSearch.setOnClickListener(v -> performSearch());

        // Allow search on Enter key press
        editTextFoodName.setOnEditorActionListener((v, actionId, event) -> {
            performSearch();
            return true;
        });
    }

    /**
     * Perform food search with input validation
     */
    private void performSearch() {
        String foodName = editTextFoodName.getText().toString();

        // Validate input
        InputValidator.ValidationResult validationResult =
                InputValidator.validateFoodInput(foodName);

        if (!validationResult.isValid()) {
            showError(validationResult.getMessage());
            return;
        }

        // Show loading state
        showLoading(true);

        // Perform search
        nutritionController.searchNutritionInfo(foodName);
    }

    /**
     * Show/hide loading indicator
     */
    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        buttonSearch.setEnabled(!isLoading);
        buttonSearch.setText(isLoading ? "Searching..." : "Search");
    }

    /**
     * Show error message to user
     */
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // NutritionDataListener implementation
    @Override
    public void onDataLoaded(NutritionResponse response) {
        runOnUiThread(() -> {
            showLoading(false);

            // Navigate to detail screen with nutrition data
            NutritionInfo nutritionInfo = response.getFoods().get(0);
            Intent intent = new Intent(MainActivity.this, NutritionDetailActivity.class);

            // Pass data using Intent extras
            intent.putExtra("food_name", nutritionInfo.getFoodName());
            intent.putExtra("calories", nutritionInfo.getCalories());
            intent.putExtra("total_fat", nutritionInfo.getTotalFat());
            intent.putExtra("saturated_fat", nutritionInfo.getSaturatedFat());
            intent.putExtra("cholesterol", nutritionInfo.getCholesterol());
            intent.putExtra("sodium", nutritionInfo.getSodium());
            intent.putExtra("total_carbohydrate", nutritionInfo.getTotalCarbohydrate());
            intent.putExtra("dietary_fiber", nutritionInfo.getDietaryFiber());
            intent.putExtra("sugars", nutritionInfo.getSugars());
            intent.putExtra("protein", nutritionInfo.getProtein());
            intent.putExtra("serving_quantity", nutritionInfo.getServingQuantity());
            intent.putExtra("serving_unit", nutritionInfo.getServingUnit());

            startActivity(intent);
        });
    }

    @Override
    public void onDataError(String error) {
        runOnUiThread(() -> {
            showLoading(false);
            showError(error);
        });
    }

    @Override
    public void onNetworkError() {
        runOnUiThread(() -> {
            showLoading(false);
            showError("No internet connection. Please check your network and try again.");
        });
    }
}