package com.example.trendingrepositories.di

import com.example.myapplication.BuildConfig
import com.example.trendingrepositories.common.network.ConnectivityObserver
import com.example.trendingrepositories.common.network.NetworkConnectivityObserver
import com.example.trendingrepositories.data.remote.intercepters.TrendingRepositoriesHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.GITHUB_DOMAIN


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideTrendingRepositoriesHeaderInterceptor(): TrendingRepositoriesHeaderInterceptor =
        TrendingRepositoriesHeaderInterceptor()


    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        trendingRepositoriesHeaderInterceptor:TrendingRepositoriesHeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(trendingRepositoriesHeaderInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver =
        networkConnectivityObserver
}