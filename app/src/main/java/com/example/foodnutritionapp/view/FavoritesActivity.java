package com.example.foodnutritionapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.viewmodel.FoodViewModel;

/**
 * Favorites screen - displays a list with RecyclerView
 */
public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView emptyView;
    private FavoritesAdapter adapter;
    private FoodViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Favorites");
        }

        initializeViews();
        initializeViewModel();
        setupRecyclerView();
        observeFavorites();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewFavorites);
        emptyView = findViewById(R.id.textViewEmpty);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new FavoritesAdapter(food -> {
            // Go to detail screen when an item is clicked
            Intent intent = new Intent(this, NutritionDetailActivity.class);
            intent.putExtra("food_name", food.getFoodName());
            intent.putExtra("calories", food.getCalories());
            intent.putExtra("total_fat", food.getTotalFat());
            intent.putExtra("saturated_fat", food.getSaturatedFat());
            intent.putExtra("cholesterol", food.getCholesterol());
            intent.putExtra("sodium", food.getSodium());
            intent.putExtra("total_carbohydrate", food.getTotalCarbohydrate());
            intent.putExtra("dietary_fiber", food.getDietaryFiber());
            intent.putExtra("sugars", food.getSugars());
            intent.putExtra("protein", food.getProtein());
            intent.putExtra("serving_quantity", food.getServingQuantity());
            intent.putExtra("serving_unit", food.getServingUnit());
            startActivity(intent);
        }, food -> {
            // When the delete button is pressed
            viewModel.removeFromFavorites(food);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void observeFavorites() {
        viewModel.getFavorites().observe(this, favorites -> {
            if (favorites == null || favorites.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                adapter.setFavorites(favorites);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}