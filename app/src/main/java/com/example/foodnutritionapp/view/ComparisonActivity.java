package com.example.foodnutritionapp.view;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.model.NutritionInfo;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * Compare two foods side by side
 */
public class ComparisonActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private TextView textFood1Name, textFood2Name;
    private TextView textFood1Calories, textFood2Calories;
    private TextView textFood1Protein, textFood2Protein;
    private TextView textFood1Carbs, textFood2Carbs;
    private TextView textFood1Fat, textFood2Fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        initializeViews();
        setupToolbar();
        loadComparisonData();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        textFood1Name = findViewById(R.id.textFood1Name);
        textFood2Name = findViewById(R.id.textFood2Name);
        textFood1Calories = findViewById(R.id.textFood1Calories);
        textFood2Calories = findViewById(R.id.textFood2Calories);
        textFood1Protein = findViewById(R.id.textFood1Protein);
        textFood2Protein = findViewById(R.id.textFood2Protein);
        textFood1Carbs = findViewById(R.id.textFood1Carbs);
        textFood2Carbs = findViewById(R.id.textFood2Carbs);
        textFood1Fat = findViewById(R.id.textFood1Fat);
        textFood2Fat = findViewById(R.id.textFood2Fat);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Compare Foods");
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadComparisonData() {
        // Get data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Food 1
            String food1Name = extras.getString("food1_name", "Food 1");
            double food1Calories = extras.getDouble("food1_calories", 0);
            double food1Protein = extras.getDouble("food1_protein", 0);
            double food1Carbs = extras.getDouble("food1_carbs", 0);
            double food1Fat = extras.getDouble("food1_fat", 0);

            // Food 2
            String food2Name = extras.getString("food2_name", "Food 2");
            double food2Calories = extras.getDouble("food2_calories", 0);
            double food2Protein = extras.getDouble("food2_protein", 0);
            double food2Carbs = extras.getDouble("food2_carbs", 0);
            double food2Fat = extras.getDouble("food2_fat", 0);

            // Display Food 1
            textFood1Name.setText(formatFoodName(food1Name));
            textFood1Calories.setText(String.format("%.0f kcal", food1Calories));
            textFood1Protein.setText(String.format("%.1fg", food1Protein));
            textFood1Carbs.setText(String.format("%.1fg", food1Carbs));
            textFood1Fat.setText(String.format("%.1fg", food1Fat));

            // Display Food 2
            textFood2Name.setText(formatFoodName(food2Name));
            textFood2Calories.setText(String.format("%.0f kcal", food2Calories));
            textFood2Protein.setText(String.format("%.1fg", food2Protein));
            textFood2Carbs.setText(String.format("%.1fg", food2Carbs));
            textFood2Fat.setText(String.format("%.1fg", food2Fat));

            // Highlight winner (lower calories = better)
            highlightWinner(food1Calories, food2Calories, textFood1Calories, textFood2Calories, true);
            highlightWinner(food1Protein, food2Protein, textFood1Protein, textFood2Protein, false);
        }
    }

    private void highlightWinner(double value1, double value2,
                                 TextView text1, TextView text2, boolean lowerIsBetter) {
        boolean food1Wins = lowerIsBetter ? (value1 < value2) : (value1 > value2);

        if (food1Wins) {
            text1.setTextColor(getResources().getColor(R.color.success_green));
        } else {
            text2.setTextColor(getResources().getColor(R.color.success_green));
        }
    }

    private String formatFoodName(String foodName) {
        if (foodName == null || foodName.isEmpty()) {
            return "Unknown";
        }
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
}