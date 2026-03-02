package com.example.foodnutritionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import com.example.foodnutritionapp.controller.InputValidator;
import com.example.foodnutritionapp.model.NutritionInfo;
import com.example.foodnutritionapp.view.FavoritesActivity;
import com.example.foodnutritionapp.view.NutritionDetailActivity;
import com.example.foodnutritionapp.view.FoodSelectionActivity;
import com.example.foodnutritionapp.viewmodel.FoodViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText editTextSearch;
    private Button buttonSearch;

    // Manual Add Views
    private TextInputEditText editManualName;
    private TextInputEditText editManualCalories;
    private Button buttonQuickAdd;

    private MaterialCardView cardFavorites;
    private MaterialCardView cardCompare;
    private TextView textTodayCalories;
    private TextView textTotalSearches;
    private Button buttonReset; // NEW: Reset button
    private FrameLayout loadingOverlay;

    private FoodViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeViewModel();
        setupObservers();
        setupEventListeners();
    }

    private void initializeViews() {
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);

        editManualName = findViewById(R.id.editManualName);
        editManualCalories = findViewById(R.id.editManualCalories);
        buttonQuickAdd = findViewById(R.id.buttonQuickAdd);

        cardFavorites = findViewById(R.id.cardFavorites);
        cardCompare = findViewById(R.id.cardCompare);
        textTodayCalories = findViewById(R.id.textTodayCalories);
        textTotalSearches = findViewById(R.id.textTotalSearches);
        buttonReset = findViewById(R.id.buttonReset); // NEW
        loadingOverlay = findViewById(R.id.loadingOverlay);

        loadingOverlay.setVisibility(View.GONE);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void setupObservers() {
        viewModel.getSearchResults().observe(this, result -> {
            if (result == null) return;
            switch (result.getStatus()) {
                case LOADING: showLoading(true); break;
                case SUCCESS:
                    showLoading(false);
                    if (result.getData() != null) {
                        navigateToDetail(result.getData());
                        editTextSearch.setText("");
                    }
                    break;
                case ERROR:
                    showLoading(false);
                    showError(result.getMessage());
                    break;
            }
        });

        viewModel.getTodayCalories().observe(this, calories -> {
            textTodayCalories.setText(calories != null ? String.format("%.0f", calories) : "0");
        });

        viewModel.getSearchCount().observe(this, count -> {
            textTotalSearches.setText(count != null ? String.valueOf(count) : "0");
        });
    }

    private void setupEventListeners() {
        buttonSearch.setOnClickListener(v -> performSearch());

        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            performSearch();
            return true;
        });

        // Manual Quick Add Logic - GRAM ONLY
        buttonQuickAdd.setOnClickListener(v -> {
            String name = editManualName.getText().toString().trim();
            String gramStr = editManualCalories.getText().toString().trim();

            if (name.isEmpty() || gramStr.isEmpty()) {
                showError("Please enter both name and grams");
                return;
            }

            try {
                double grams = Double.parseDouble(gramStr);
                if (grams <= 0) {
                    showError("Please enter a positive number");
                    return;
                }

                // Estimate: 1 gram = ~2-3 kcal (average for mixed food)
                double estimatedCalories = grams * 2.5;
                viewModel.addSimpleIntake(name, estimatedCalories);

                // Clear inputs
                editManualName.setText("");
                editManualCalories.setText("");
                Toast.makeText(this, "Added " + name + " (" + grams + "g, ~" + String.format("%.0f", estimatedCalories) + " kcal)", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                showError("Please enter a valid number for grams");
            }
        });

        // NEW: Reset button logic
        buttonReset.setOnClickListener(v -> showResetConfirmationDialog());

        cardFavorites.setOnClickListener(v -> startActivity(new Intent(this, FavoritesActivity.class)));
        cardCompare.setOnClickListener(v -> startActivity(new Intent(this, FoodSelectionActivity.class)));

        textTodayCalories.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.foodnutritionapp.view.DailyTrackerActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.textTodayCalories).getParent().requestLayout();
        ((View) textTodayCalories.getParent()).setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.foodnutritionapp.view.DailyTrackerActivity.class);
            startActivity(intent);
        });
    }

    // NEW: Show reset confirmation dialog
    private void showResetConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Reset Data")
                .setMessage("Are you sure you want to reset today's calories and search history? This action cannot be undone.")
                .setPositiveButton("Reset", (dialog, which) -> {
                    viewModel.resetTodayData();
                    Toast.makeText(this, "Data reset successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void performSearch() {
        String query = editTextSearch.getText().toString();
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(query);
        if (!result.isValid()) {
            showError(result.getMessage());
            return;
        }
        viewModel.searchFood(query.trim());
    }

    private void navigateToDetail(NutritionInfo info) {
        Intent intent = new Intent(this, NutritionDetailActivity.class);
        intent.putExtra("food_name", info.getFoodName());
        intent.putExtra("calories", info.getCalories());
        intent.putExtra("total_fat", info.getTotalFat());
        intent.putExtra("saturated_fat", info.getSaturatedFat());
        intent.putExtra("cholesterol", info.getCholesterol());
        intent.putExtra("sodium", info.getSodium());
        intent.putExtra("total_carbohydrate", info.getTotalCarbohydrate());
        intent.putExtra("dietary_fiber", info.getDietaryFiber());
        intent.putExtra("sugars", info.getSugars());
        intent.putExtra("protein", info.getProtein());
        intent.putExtra("serving_quantity", info.getServingQuantity());
        intent.putExtra("serving_unit", info.getServingUnit());
        startActivity(intent);
    }

    private void showLoading(boolean isLoading) {
        loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // NEW: Options menu for dark mode toggle
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_theme) {
            toggleDarkMode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleDarkMode() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(this, "Light mode enabled", Toast.LENGTH_SHORT).show();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Toast.makeText(this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
        }
    }
}