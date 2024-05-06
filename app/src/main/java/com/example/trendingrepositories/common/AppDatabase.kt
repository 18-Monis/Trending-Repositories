package com.example.trendingrepositories.common


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trendingrepositories.data.local.dao.TrendingRepositoryDAO
import com.example.trendingrepositories.data.local.entities.TrendingRepositoryEntity

@Database(entities = [TrendingRepositoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trendingRepositoryDAO(): TrendingRepositoryDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "trending_repositories_database"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}