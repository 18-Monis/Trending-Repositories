package com.example.trendingrepositories.data.local.repositories

import com.example.trendingrepositories.data.local.dao.TrendingRepositoryDAO
import com.example.trendingrepositories.data.local.entities.TrendingRepositoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteRepositoriesManagerImpl @Inject constructor(private val trendingRepositoryDAO: TrendingRepositoryDAO) :
    FavouriteRepositoriesManager {

    override fun getFavouriteRepositories(): Flow<List<TrendingRepositoryEntity>> {
        return trendingRepositoryDAO.getAllRepositories()
    }

    override suspend fun addRepository(trendingRepositoryEntity: TrendingRepositoryEntity) {
        trendingRepositoryDAO.addRepository(trendingRepositoryEntity)
    }

    override suspend fun deleteRepository(id: Long) {
        trendingRepositoryDAO.deleteRepository(id)
    }
}