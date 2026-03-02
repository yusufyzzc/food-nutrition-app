package com.example.foodnutritionapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.model.NutritionInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for food selection with checkboxes
 */
public class FoodSelectionAdapter extends RecyclerView.Adapter<FoodSelectionAdapter.SelectionViewHolder> {
    private List<NutritionInfo> foods = new ArrayList<>();
    private List<NutritionInfo> selectedFoods = new ArrayList<>();
    private final OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(NutritionInfo food);
    }

    public FoodSelectionAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFoods(List<NutritionInfo> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    public void setSelectedFoods(List<NutritionInfo> selectedFoods) {
        this.selectedFoods = selectedFoods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_selection, parent, false);
        return new SelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectionViewHolder holder, int position) {
        NutritionInfo food = foods.get(position);
        holder.bind(food, selectedFoods.contains(food));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class SelectionViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private final TextView textName;
        private final TextView textCalories;

        public SelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxSelect);
            textName = itemView.findViewById(R.id.textSelectionName);
            textCalories = itemView.findViewById(R.id.textSelectionCalories);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(foods.get(position));
                }
            });

            checkBox.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(foods.get(position));
                }
            });
        }

        public void bind(NutritionInfo food, boolean isSelected) {
            textName.setText(formatFoodName(food.getFoodName()));
            textCalories.setText(String.format("%.0f kcal", food.getCalories()));
            checkBox.setChecked(isSelected);
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