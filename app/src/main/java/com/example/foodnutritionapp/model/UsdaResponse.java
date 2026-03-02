package com.example.foodnutritionapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * USDA API Response Models
 */
public class UsdaResponse {
    @SerializedName("foods")
    private List<UsdaFood> foods;

    public List<UsdaFood> getFoods() {
        return foods;
    }

    public void setFoods(List<UsdaFood> foods) {
        this.foods = foods;
    }

    // ============================================
    // USDA Food Object (inner class olarak public yapıldı)
    // ============================================
    public static class UsdaFood {
        @SerializedName("fdcId")
        private int fdcId;

        @SerializedName("description")
        private String description;

        @SerializedName("foodNutrients")
        private List<UsdaNutrient> nutrients;

        public int getFdcId() {
            return fdcId;
        }

        public String getDescription() {
            return description;
        }

        public List<UsdaNutrient> getNutrients() {
            return nutrients;
        }

        /**
         * Convert USDA Food to NutritionInfo
         * IMPORTANT: Bu metod public olmalı!
         */
        public NutritionInfo toNutritionInfo() {
            NutritionInfo info = new NutritionInfo();
            info.setFoodName(description != null ? description : "Unknown Food");
            info.setServingQuantity(100);
            info.setServingUnit("g");
            info.setServingWeightGrams(100);

            // Parse nutrients
            if (nutrients != null) {
                for (UsdaNutrient nutrient : nutrients) {
                    if (nutrient == null || nutrient.getNutrientName() == null) {
                        continue;
                    }

                    String name = nutrient.getNutrientName().toLowerCase();
                    double value = nutrient.getValue();

                    // Energy/Calories
                    if (name.contains("energy")) {
                        // USDA returns kcal directly
                        info.setCalories(value);
                    }
                    // Fats
                    else if (name.contains("total lipid") || name.contains("fat, total")) {
                        info.setTotalFat(value);
                    }
                    else if (name.contains("saturated")) {
                        info.setSaturatedFat(value);
                    }
                    // Cholesterol
                    else if (name.contains("cholesterol")) {
                        info.setCholesterol(value);
                    }
                    // Sodium
                    else if (name.contains("sodium")) {
                        info.setSodium(value);
                    }
                    // Carbohydrates
                    else if (name.contains("carbohydrate")) {
                        info.setTotalCarbohydrate(value);
                    }
                    else if (name.contains("fiber")) {
                        info.setDietaryFiber(value);
                    }
                    else if (name.contains("sugars, total") || name.contains("total sugars")) {
                        info.setSugars(value);
                    }
                    // Protein
                    else if (name.contains("protein")) {
                        info.setProtein(value);
                    }
                    // Potassium
                    else if (name.contains("potassium")) {
                        info.setPotassium(value);
                    }
                }
            }

            return info;
        }
    }

    // ============================================
    // USDA Nutrient Object
    // ============================================
    public static class UsdaNutrient {
        @SerializedName("nutrientName")
        private String nutrientName;

        @SerializedName("value")
        private double value;

        @SerializedName("unitName")
        private String unitName;

        public String getNutrientName() {
            return nutrientName;
        }

        public double getValue() {
            return value;
        }

        public String getUnitName() {
            return unitName;
        }
    }
}