package com.example.trendingrepositories.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trendingrepositories.data.remote.models.TrendingRepository
import com.example.trendingrepositories.data.remote.services.TrendingRepositoriesService
import com.example.trendingrepositories.utils.DateUtils
import java.util.Date
import javax.inject.Inject

class TrendingRepositoriesPagingSource @Inject constructor(
    private val trendingRepositoriesService: TrendingRepositoriesService,
    private val date: Date
) : PagingSource<Int, TrendingRepository>() {
    override fun getRefreshKey(state: PagingState<Int, TrendingRepository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingRepository> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val data =
                trendingRepositoriesService.search(
                    date = DateUtils.formatDate(date),
                    page = page
                ).body()?.items.orEmpty()
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}