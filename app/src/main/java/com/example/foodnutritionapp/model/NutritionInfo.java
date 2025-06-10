package com.example.foodnutritionapp.model;

import com.google.gson.annotations.SerializedName;

public class NutritionInfo {
    @SerializedName("food_name")
    private String foodName;

    @SerializedName("serving_qty")
    private double servingQuantity;

    @SerializedName("serving_unit")
    private String servingUnit;

    @SerializedName("serving_weight_grams")
    private double servingWeightGrams;

    @SerializedName("nf_calories")
    private double calories;

    @SerializedName("nf_total_fat")
    private double totalFat;

    @SerializedName("nf_saturated_fat")
    private double saturatedFat;

    @SerializedName("nf_cholesterol")
    private double cholesterol;

    @SerializedName("nf_sodium")
    private double sodium;

    @SerializedName("nf_total_carbohydrate")
    private double totalCarbohydrate;

    @SerializedName("nf_dietary_fiber")
    private double dietaryFiber;

    @SerializedName("nf_sugars")
    private double sugars;

    @SerializedName("nf_protein")
    private double protein;

    @SerializedName("nf_potassium")
    private double potassium;

    // Constructors
    public NutritionInfo() {}

    // Getters and Setters
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public double getServingQuantity() { return servingQuantity; }
    public void setServingQuantity(double servingQuantity) { this.servingQuantity = servingQuantity; }

    public String getServingUnit() { return servingUnit; }
    public void setServingUnit(String servingUnit) { this.servingUnit = servingUnit; }

    public double getServingWeightGrams() { return servingWeightGrams; }
    public void setServingWeightGrams(double servingWeightGrams) { this.servingWeightGrams = servingWeightGrams; }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public double getTotalFat() { return totalFat; }
    public void setTotalFat(double totalFat) { this.totalFat = totalFat; }

    public double getSaturatedFat() { return saturatedFat; }
    public void setSaturatedFat(double saturatedFat) { this.saturatedFat = saturatedFat; }

    public double getCholesterol() { return cholesterol; }
    public void setCholesterol(double cholesterol) { this.cholesterol = cholesterol; }

    public double getSodium() { return sodium; }
    public void setSodium(double sodium) { this.sodium = sodium; }

    public double getTotalCarbohydrate() { return totalCarbohydrate; }
    public void setTotalCarbohydrate(double totalCarbohydrate) { this.totalCarbohydrate = totalCarbohydrate; }

    public double getDietaryFiber() { return dietaryFiber; }
    public void setDietaryFiber(double dietaryFiber) { this.dietaryFiber = dietaryFiber; }

    public double getSugars() { return sugars; }
    public void setSugars(double sugars) { this.sugars = sugars; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getPotassium() { return potassium; }
    public void setPotassium(double potassium) { this.potassium = potassium; }
}