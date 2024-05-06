package com.example.trendingrepositories.di

import android.content.Context
import com.example.trendingrepositories.common.AppDatabase
import com.example.trendingrepositories.data.local.dao.TrendingRepositoryDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideTrendingRepositoryDAO(appDatabase: AppDatabase): TrendingRepositoryDAO {
        return appDatabase.trendingRepositoryDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}