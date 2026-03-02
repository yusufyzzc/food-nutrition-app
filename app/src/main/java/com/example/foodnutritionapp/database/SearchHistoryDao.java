package com.example.foodnutritionapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.foodnutritionapp.model.SearchHistory;
import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchHistory searchHistory);

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 10")
    LiveData<List<SearchHistory>> getRecentSearches();

    @Query("DELETE FROM search_history WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM search_history")
    void clearAll();

    @Query("SELECT COUNT(*) FROM search_history")
    LiveData<Integer> getSearchCount();
}