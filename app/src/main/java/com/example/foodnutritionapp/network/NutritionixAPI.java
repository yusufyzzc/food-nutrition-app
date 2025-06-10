package com.example.foodnutritionapp.network;

import com.example.foodnutritionapp.model.NutritionResponse;
import com.example.foodnutritionapp.model.SearchRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NutritionixAPI {
    String BASE_URL = "https://trackapi.nutritionix.com/";

    @Headers({
            "Content-Type: application/json",
            "x-app-id: \n" +
                    "9f719a41",
            "x-app-key: \n" +
                    "0336b9f78319b9b197c39b0f3b93d684"
    })
    @POST("v2/natural/nutrients")
    Call<NutritionResponse> getNutritionInfo(@Body SearchRequest request);
}