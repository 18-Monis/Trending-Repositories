package com.example.trendingrepositories.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendingrepositories.data.local.entities.TrendingRepositoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingRepositoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepository(repository: TrendingRepositoryEntity)

    @Query("DELETE FROM trending_repositories WHERE repositoryId = :id")
    suspend fun deleteRepository(id: Long)

    @Query("SELECT * FROM trending_repositories")
    fun getAllRepositories(): Flow<List<TrendingRepositoryEntity>>
}