package com.example.foodnutritionapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodnutritionapp.R;
import com.example.foodnutritionapp.model.NutritionInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter - Favoriler listesi
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {
    private List<NutritionInfo> favorites = new ArrayList<>();
    private final OnItemClickListener clickListener;
    private final OnDeleteClickListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(NutritionInfo food);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(NutritionInfo food);
    }

    public FavoritesAdapter(OnItemClickListener clickListener,
                            OnDeleteClickListener deleteListener) {
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }

    public void setFavorites(List<NutritionInfo> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        NutritionInfo food = favorites.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewCalories;
        private final ImageButton buttonDelete;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewFoodName);
            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

        public void bind(NutritionInfo food) {
            textViewName.setText(formatFoodName(food.getFoodName()));
            textViewCalories.setText(String.format("%.0f kcal", food.getCalories()));

            itemView.setOnClickListener(v -> clickListener.onItemClick(food));
            buttonDelete.setOnClickListener(v -> deleteListener.onDeleteClick(food));
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