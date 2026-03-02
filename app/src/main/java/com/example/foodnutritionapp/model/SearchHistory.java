package com.example.foodnutritionapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Search History Entity
 * Stores recent food searches
 */
@Entity(tableName = "search_history")
public class SearchHistory {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String query;

    private long timestamp;

    public SearchHistory(@NonNull String query) {
        this.query = query;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getQuery() { return query; }
    public void setQuery(@NonNull String query) { this.query = query; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}