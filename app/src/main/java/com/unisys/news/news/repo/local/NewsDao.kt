package com.unisys.news.news.repo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unisys.news.news.repo.dto.Article

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<Article>)

    @Query("SELECT * from news_table ORDER BY id ASC")
    fun all(): List<Article>
}