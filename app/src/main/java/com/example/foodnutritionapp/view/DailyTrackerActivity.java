package com.example.foodnutritionapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.viewmodel.FoodViewModel;

public class DailyTrackerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextView textTotalCalories; // Added to show total at the top
    private DailyTrackerAdapter adapter;
    private FoodViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tracker); // We will create this layout next

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Today's Intake");
        }

        initializeViews();
        initializeViewModel();
        setupRecyclerView();
        observeDailyIntake();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewDaily);
        emptyView = findViewById(R.id.textViewEmpty);
        textTotalCalories = findViewById(R.id.textTotalCaloriesHeader);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
    }

    private void setupRecyclerView() {
        adapter = new DailyTrackerAdapter(intake -> {
            // Optional: Click listener if you want to see details later
            // For now, we just list them.
        }, intake -> {
            // Delete button logic
            viewModel.removeFromDailyIntake(intake);
            Toast.makeText(this, "Removed from daily list", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void observeDailyIntake() {
        // Observer for the List
        viewModel.getTodayIntakes().observe(this, intakes -> {
            if (intakes == null || intakes.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                adapter.setDailyIntakes(intakes);
            }
        });

        // Observer for the Total Calories (Header)
        viewModel.getTodayCalories().observe(this, total -> {
            if (total != null) {
                textTotalCalories.setText(String.format("Total: %.0f kcal", total));
            } else {
                textTotalCalories.setText("Total: 0 kcal");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}