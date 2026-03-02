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

public class DailyTrackerAdapter extends RecyclerView.Adapter<DailyTrackerAdapter.DailyViewHolder> {
    private List<DailyIntake> dailyIntakes = new ArrayList<>();
    private final OnItemClickListener clickListener;
    private final OnDeleteClickListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(DailyIntake intake);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(DailyIntake intake);
    }

    public DailyTrackerAdapter(OnItemClickListener clickListener,
                               OnDeleteClickListener deleteListener) {
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    public void setDailyIntakes(List<DailyIntake> dailyIntakes) {
        this.dailyIntakes = dailyIntakes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Re-using the same item layout because the structure (Name, Calories, Delete) is identical
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        DailyIntake intake = dailyIntakes.get(position);
        holder.bind(intake);
    }

    @Override
    public int getItemCount() {
        return dailyIntakes.size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewCalories;
        private final ImageButton buttonDelete;

        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewFoodName);
            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

        public void bind(DailyIntake intake) {
            textViewName.setText(formatFoodName(intake.getFoodName()));
            textViewCalories.setText(String.format("%.0f kcal", intake.getCalories()));

            itemView.setOnClickListener(v -> clickListener.onItemClick(intake));

            // Set trash icon color to red (if not already set in XML)
            buttonDelete.setColorFilter(itemView.getContext().getResources().getColor(R.color.error_red));
            buttonDelete.setOnClickListener(v -> deleteListener.onDeleteClick(intake));
        }

        private String formatFoodName(String foodName) {
            if (foodName == null || foodName.isEmpty()) return "Unknown Food";
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