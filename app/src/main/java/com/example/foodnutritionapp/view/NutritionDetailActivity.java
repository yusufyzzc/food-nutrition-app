package com.example.foodnutritionapp.view;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.model.NutritionInfo;
import com.example.foodnutritionapp.viewmodel.FoodViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Locale;

public class NutritionDetailActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private TextView textViewFoodNameHeader;
    private TextView textViewServingInfoHeader;
    private TextView textViewCalories;
    private TextView textViewTotalFat;
    private TextView textViewTotalCarbs;
    private TextView textViewProtein;
    private FloatingActionButton fabFavorite;
    private MaterialButton buttonAddToDaily;
    private TextView textSatFatValue;
    private TextView textFiberValue;
    private TextView textSugarsValue;
    private TextView textCholesterolValue;
    private TextView textSodiumValue;
    private FoodViewModel viewModel;
    private NutritionInfo currentFood;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_detail);
        initializeViews();
        initializeViewModel();
        displayNutritionData();
        setupFavoriteButton();
        setupToolbar();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        textViewFoodNameHeader = findViewById(R.id.textViewFoodNameHeader);
        textViewServingInfoHeader = findViewById(R.id.textViewServingInfoHeader);
        textViewCalories = findViewById(R.id.textViewCalories);
        textViewTotalFat = findViewById(R.id.textViewTotalFat);
        textViewTotalCarbs = findViewById(R.id.textViewTotalCarbs);
        textViewProtein = findViewById(R.id.textViewProtein);
        fabFavorite = findViewById(R.id.fabFavorite);
        buttonAddToDaily = findViewById(R.id.buttonAddToDaily);
        textSatFatValue = findViewById(R.id.rowSaturatedFat).findViewById(R.id.text_nutrient_value);
        textFiberValue = findViewById(R.id.rowDietaryFiber).findViewById(R.id.text_nutrient_value);
        textSugarsValue = findViewById(R.id.rowSugars).findViewById(R.id.text_nutrient_value);
        textCholesterolValue = findViewById(R.id.rowCholesterol).findViewById(R.id.text_nutrient_value);
        textSodiumValue = findViewById(R.id.rowSodium).findViewById(R.id.text_nutrient_value);
        ((TextView) findViewById(R.id.rowSaturatedFat).findViewById(R.id.text_nutrient_name)).setText(R.string.saturated_fat);
        ((TextView) findViewById(R.id.rowDietaryFiber).findViewById(R.id.text_nutrient_name)).setText(R.string.dietary_fiber);
        ((TextView) findViewById(R.id.rowSugars).findViewById(R.id.text_nutrient_name)).setText(R.string.sugars);
        ((TextView) findViewById(R.id.rowCholesterol).findViewById(R.id.text_nutrient_name)).setText(R.string.cholesterol);
        ((TextView) findViewById(R.id.rowSodium).findViewById(R.id.text_nutrient_name)).setText(R.string.sodium);

        buttonAddToDaily.setOnClickListener(v -> showAddIntakeDialog());
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);
        if (collapsingToolbar != null) collapsingToolbar.setTitleEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    // FIXED: Only Grams option
    private void showAddIntakeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add to Daily Intake");
        builder.setMessage("Enter amount in grams:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("e.g., 150");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String valueStr = input.getText().toString().trim();
            if (!valueStr.isEmpty()) {
                try {
                    double grams = Double.parseDouble(valueStr);
                    if (grams <= 0) {
                        Toast.makeText(this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Always use grams
                    viewModel.addWeightedIntake(currentFood, grams, true);
                    Toast.makeText(this, "Added " + grams + "g to daily intake", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void displayNutritionData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentFood = new NutritionInfo();
            String foodName = extras.getString("food_name", "Unknown Food");
            currentFood.setFoodName(foodName);
            currentFood.setCalories(extras.getDouble("calories", 0));
            currentFood.setTotalFat(extras.getDouble("total_fat", 0));
            currentFood.setSaturatedFat(extras.getDouble("saturated_fat", 0));
            currentFood.setCholesterol(extras.getDouble("cholesterol", 0));
            currentFood.setSodium(extras.getDouble("sodium", 0));
            currentFood.setTotalCarbohydrate(extras.getDouble("total_carbohydrate", 0));
            currentFood.setDietaryFiber(extras.getDouble("dietary_fiber", 0));
            currentFood.setSugars(extras.getDouble("sugars", 0));
            currentFood.setProtein(extras.getDouble("protein", 0));

            textViewFoodNameHeader.setText(formatFoodName(foodName));
            textViewServingInfoHeader.setText(R.string.per_100g);
            textViewCalories.setText(String.format(Locale.getDefault(), "%.0f", currentFood.getCalories()));
            textViewTotalFat.setText(String.format(Locale.getDefault(), "%.1f", currentFood.getTotalFat()));
            textViewTotalCarbs.setText(String.format(Locale.getDefault(), "%.1f", currentFood.getTotalCarbohydrate()));
            textViewProtein.setText(String.format(Locale.getDefault(), "%.1f", currentFood.getProtein()));
            textSatFatValue.setText(String.format(Locale.getDefault(), "%.1f g", currentFood.getSaturatedFat()));
            textFiberValue.setText(String.format(Locale.getDefault(), "%.1f g", currentFood.getDietaryFiber()));
            textSugarsValue.setText(String.format(Locale.getDefault(), "%.1f g", currentFood.getSugars()));
            textCholesterolValue.setText(String.format(Locale.getDefault(), "%.0f mg", currentFood.getCholesterol()));
            textSodiumValue.setText(String.format(Locale.getDefault(), "%.0f mg", currentFood.getSodium()));
        }
    }

    private void setupFavoriteButton() {
        if (currentFood != null) {
            viewModel.isFavorite(currentFood.getFoodName()).observe(this, isFav -> {
                isFavorite = isFav != null && isFav;
                updateFavoriteIcon();
            });
            fabFavorite.setOnClickListener(v -> {
                if (isFavorite) {
                    viewModel.removeFromFavorites(currentFood);
                } else {
                    viewModel.addToFavorites(currentFood);
                }
            });
        }
    }

    private void updateFavoriteIcon() {
        fabFavorite.setImageResource(isFavorite ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_border);
    }

    private String formatFoodName(String foodName) {
        if (foodName == null || foodName.isEmpty()) return "Unknown Food";
        String[] words = foodName.toLowerCase().split(" ");
        StringBuilder formatted = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formatted.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return formatted.toString().trim();
    }
}