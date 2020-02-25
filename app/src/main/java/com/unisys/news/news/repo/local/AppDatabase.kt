package com.unisys.news.news.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unisys.news.news.repo.dto.Article

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

}