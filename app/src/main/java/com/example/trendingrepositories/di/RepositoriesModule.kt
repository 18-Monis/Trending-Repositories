package com.example.trendingrepositories.di

import com.example.trendingrepositories.data.local.repositories.FavouriteRepositoriesManager
import com.example.trendingrepositories.data.local.repositories.FavouriteRepositoriesManagerImpl
import com.example.trendingrepositories.data.remote.repositories.TrendingRepositoriesManager
import com.example.trendingrepositories.data.remote.repositories.TrendingRepositoriesManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideTrendingRepositoriesManager(trendingRepositoriesManagerImpl: TrendingRepositoriesManagerImpl): TrendingRepositoriesManager =
        trendingRepositoriesManagerImpl

    @Provides
    @Singleton
    fun provideFavouriteRepositoriesManager(favouriteRepositoriesManagerImpl: FavouriteRepositoriesManagerImpl): FavouriteRepositoriesManager =
        favouriteRepositoriesManagerImpl
}