package com.example.trendingrepositories.data.local.repositories

import com.example.trendingrepositories.data.local.entities.TrendingRepositoryEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteRepositoriesManager {

    fun getFavouriteRepositories(): Flow<List<TrendingRepositoryEntity>>
    suspend fun addRepository(trendingRepositoryEntity: TrendingRepositoryEntity)
    suspend fun deleteRepository(id: Long)
}