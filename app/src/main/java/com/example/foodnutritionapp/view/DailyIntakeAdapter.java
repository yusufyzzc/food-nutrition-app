package com.example.foodnutritionapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.model.DailyIntake;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter for Daily Intake list
 */
public class DailyIntakeAdapter extends RecyclerView.Adapter<DailyIntakeAdapter.IntakeViewHolder> {
    private List<DailyIntake> intakes = new ArrayList<>();
    private final OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(DailyIntake intake);
    }

    public DailyIntakeAdapter(OnDeleteClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setIntakes(List<DailyIntake> intakes) {
        this.intakes = intakes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IntakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_intake, parent, false);
        return new IntakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntakeViewHolder holder, int position) {
        DailyIntake intake = intakes.get(position);
        holder.bind(intake);
    }

    @Override
    public int getItemCount() {
        return intakes.size();
    }

    class IntakeViewHolder extends RecyclerView.ViewHolder {
        private final TextView textFoodName;
        private final TextView textCalories;
        private final TextView textMacros;
        private final ImageButton buttonDelete;

        public IntakeViewHolder(@NonNull View itemView) {
            super(itemView);
            textFoodName = itemView.findViewById(R.id.textIntakeFoodName);
            textCalories = itemView.findViewById(R.id.textIntakeCalories);
            textMacros = itemView.findViewById(R.id.textIntakeMacros);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteIntake);
        }

        public void bind(DailyIntake intake) {
            textFoodName.setText(formatFoodName(intake.getFoodName()));
            textCalories.setText(String.format("%.0f kcal", intake.getCalories()));
            textMacros.setText(String.format("P: %.1fg  C: %.1fg  F: %.1fg",
                    intake.getProtein(),
                    intake.getCarbs(),
                    intake.getFat()));

            buttonDelete.setOnClickListener(v -> deleteListener.onDeleteClick(intake));
        }

        private String formatFoodName(String foodName) {
            if (foodName == null || foodName.isEmpty()) {
                return "Unknown Food";
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
}