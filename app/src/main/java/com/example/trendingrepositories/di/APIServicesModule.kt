package com.example.trendingrepositories.di

import com.example.trendingrepositories.data.remote.services.TrendingRepositoriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIServicesModule {

    @Provides
    @Singleton
    fun provideTrendingRepositoriesService(retrofit: Retrofit): TrendingRepositoriesService =
        retrofit.create(TrendingRepositoriesService::class.java)
}