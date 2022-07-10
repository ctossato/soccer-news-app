package me.dio.soccernews.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import me.dio.soccernews.domain.News;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news WHERE favorite = 1")
    LiveData<List<News>> loadFavoriteNews();

    @Query("SELECT * FROM news WHERE favorite = 1")
    List<News> loadFavoriteNews_non();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(News news);
}
