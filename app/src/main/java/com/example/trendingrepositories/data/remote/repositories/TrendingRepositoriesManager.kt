package com.example.trendingrepositories.data.remote.repositories

import androidx.paging.PagingData
import com.example.trendingrepositories.data.remote.models.TrendingRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TrendingRepositoriesManager {

    fun getTrendingRepositories(date: Date): Flow<PagingData<TrendingRepository>>
}