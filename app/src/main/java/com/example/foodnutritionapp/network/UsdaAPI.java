package com.example.foodnutritionapp.network;

import com.example.foodnutritionapp.model.UsdaResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsdaAPI {
    String BASE_URL = "https://api.nal.usda.gov/fdc/v1/";

    // USDA API anahtarını buraya ekle (https://fdc.nal.usda.gov/api-key-signup.html)
    String API_KEY = "VEc36HgVCA1DXWPRdDU9XFdU8hCPBRTUn9qfNmPI";

    @GET("foods/search")
    Call<UsdaResponse> searchFood(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("pageSize") int pageSize
    );
}