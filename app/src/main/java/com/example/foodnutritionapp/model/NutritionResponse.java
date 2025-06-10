package com.example.foodnutritionapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NutritionResponse {
    @SerializedName("foods")
    private List<NutritionInfo> foods;

    public NutritionResponse() {}

    public List<NutritionInfo> getFoods() { return foods; }
    public void setFoods(List<NutritionInfo> foods) { this.foods = foods; }
}