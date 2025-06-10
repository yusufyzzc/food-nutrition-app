package com.example.foodnutritionapp.model;

import com.google.gson.annotations.SerializedName;

public class SearchRequest {
    @SerializedName("query")
    private String query;

    public SearchRequest(String query) {
        this.query = query;
    }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
}