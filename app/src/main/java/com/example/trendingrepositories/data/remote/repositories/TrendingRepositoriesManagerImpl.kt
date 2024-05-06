package com.example.trendingrepositories.data.remote.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trendingrepositories.data.paging.TrendingRepositoriesPagingSource
import com.example.trendingrepositories.data.remote.models.TrendingRepository
import com.example.trendingrepositories.data.remote.services.TrendingRepositoriesService
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class TrendingRepositoriesManagerImpl @Inject constructor(private val trendingRepositoriesService: TrendingRepositoriesService) :
    TrendingRepositoriesManager {

    override fun getTrendingRepositories(date: Date): Flow<PagingData<TrendingRepository>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TrendingRepositoriesPagingSource(
                    trendingRepositoriesService = trendingRepositoriesService,
                    date = date
                )
            }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}