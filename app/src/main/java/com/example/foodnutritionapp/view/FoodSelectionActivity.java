package com.example.foodnutritionapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.model.NutritionInfo;
import com.example.foodnutritionapp.viewmodel.FoodViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;

/**
 * Select 2 foods from favorites to compare
 */
public class FoodSelectionActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private TextView textSelection;
    private Button buttonCompare;
    private FoodSelectionAdapter adapter;
    private FoodViewModel viewModel;

    private List<NutritionInfo> selectedFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);

        initializeViews();
        setupToolbar();
        initializeViewModel();
        setupRecyclerView();
        observeFavorites();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerSelection);
        textSelection = findViewById(R.id.textSelection);
        buttonCompare = findViewById(R.id.buttonCompare);

        buttonCompare.setEnabled(false);
        updateSelectionText();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Select Foods to Compare");
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new FoodSelectionAdapter(food -> {
            // Item clicked - toggle selection
            if (selectedFoods.contains(food)) {
                selectedFoods.remove(food);
            } else {
                if (selectedFoods.size() < 2) {
                    selectedFoods.add(food);
                } else {
                    Toast.makeText(this, "You can only select 2 foods",
                            Toast.LENGTH_SHORT).show();
                }
            }

            adapter.setSelectedFoods(selectedFoods);
            updateSelectionText();
            buttonCompare.setEnabled(selectedFoods.size() == 2);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void observeFavorites() {
        viewModel.getFavorites().observe(this, favorites -> {
            if (favorites == null || favorites.isEmpty()) {
                Toast.makeText(this, "No favorites found. Add some first!",
                        Toast.LENGTH_LONG).show();
                finish();
            } else if (favorites.size() < 2) {
                Toast.makeText(this, "You need at least 2 favorites to compare",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                adapter.setFoods(favorites);
            }
        });
    }

    private void updateSelectionText() {
        textSelection.setText(String.format("Selected: %d / 2", selectedFoods.size()));
    }

    public void onCompareClick(View view) {
        if (selectedFoods.size() == 2) {
            NutritionInfo food1 = selectedFoods.get(0);
            NutritionInfo food2 = selectedFoods.get(1);

            Intent intent = new Intent(this, ComparisonActivity.class);

            // Food 1 data
            intent.putExtra("food1_name", food1.getFoodName());
            intent.putExtra("food1_calories", food1.getCalories());
            intent.putExtra("food1_protein", food1.getProtein());
            intent.putExtra("food1_carbs", food1.getTotalCarbohydrate());
            intent.putExtra("food1_fat", food1.getTotalFat());

            // Food 2 data
            intent.putExtra("food2_name", food2.getFoodName());
            intent.putExtra("food2_calories", food2.getCalories());
            intent.putExtra("food2_protein", food2.getProtein());
            intent.putExtra("food2_carbs", food2.getTotalCarbohydrate());
            intent.putExtra("food2_fat", food2.getTotalFat());

            startActivity(intent);
        }
    }
}