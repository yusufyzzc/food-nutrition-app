package com.example.foodnutritionapp.view;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodnutritionapp.R;

/**
 * Detail Activity showing nutrition information
 * Follows MVC pattern as View component
 */
public class NutritionDetailActivity extends AppCompatActivity {

    private TextView textViewFoodName;
    private TextView textViewCalories;
    private TextView textViewTotalFat;
    private TextView textViewSaturatedFat;
    private TextView textViewCholesterol;
    private TextView textViewSodium;
    private TextView textViewTotalCarbs;
    private TextView textViewDietaryFiber;
    private TextView textViewSugars;
    private TextView textViewProtein;
    private TextView textViewServingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_detail);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nutrition Facts");
        }

        initializeViews();
        displayNutritionData();
    }

    /**
     * Initialize UI components
     */
    private void initializeViews() {
        textViewFoodName = findViewById(R.id.textViewFoodName);
        textViewCalories = findViewById(R.id.textViewCalories);
        textViewTotalFat = findViewById(R.id.textViewTotalFat);
        textViewSaturatedFat = findViewById(R.id.textViewSaturatedFat);
        textViewCholesterol = findViewById(R.id.textViewCholesterol);
        textViewSodium = findViewById(R.id.textViewSodium);
        textViewTotalCarbs = findViewById(R.id.textViewTotalCarbs);
        textViewDietaryFiber = findViewById(R.id.textViewDietaryFiber);
        textViewSugars = findViewById(R.id.textViewSugars);
        textViewProtein = findViewById(R.id.textViewProtein);
        textViewServingInfo = findViewById(R.id.textViewServingInfo);
    }

    /**
     * Display nutrition data received from intent
     */
    private void displayNutritionData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String foodName = extras.getString("food_name", "Unknown Food");
            double calories = extras.getDouble("calories", 0);
            double totalFat = extras.getDouble("total_fat", 0);
            double saturatedFat = extras.getDouble("saturated_fat", 0);
            double cholesterol = extras.getDouble("cholesterol", 0);
            double sodium = extras.getDouble("sodium", 0);
            double totalCarbs = extras.getDouble("total_carbohydrate", 0);
            double dietaryFiber = extras.getDouble("dietary_fiber", 0);
            double sugars = extras.getDouble("sugars", 0);
            double protein = extras.getDouble("protein", 0);
            double servingQuantity = extras.getDouble("serving_quantity", 0);
            String servingUnit = extras.getString("serving_unit", "");

            // Display data with proper formatting
            textViewFoodName.setText(formatFoodName(foodName));
            textViewCalories.setText(String.format("%.0f cal", calories));
            textViewTotalFat.setText(String.format("%.1f g", totalFat));
            textViewSaturatedFat.setText(String.format("%.1f g", saturatedFat));
            textViewCholesterol.setText(String.format("%.0f mg", cholesterol));
            textViewSodium.setText(String.format("%.0f mg", sodium));
            textViewTotalCarbs.setText(String.format("%.1f g", totalCarbs));
            textViewDietaryFiber.setText(String.format("%.1f g", dietaryFiber));
            textViewSugars.setText(String.format("%.1f g", sugars));
            textViewProtein.setText(String.format("%.1f g", protein));
            textViewServingInfo.setText(String.format("Per %.0f %s", servingQuantity, servingUnit));
        }
    }

    /**
     * Format food name for display
     */
    private String formatFoodName(String foodName) {
        if (foodName == null || foodName.isEmpty()) {
            return "Unknown Food";
        }

        // Capitalize first letter of each word
        String[] words = foodName.toLowerCase().split(" ");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                formatted.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    formatted.append(word.substring(1));
                }
                formatted.append(" ");
            }
        }

        return formatted.toString().trim();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}