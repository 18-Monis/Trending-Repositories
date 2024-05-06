package com.example.trendingrepositories.data.remote.services

import com.example.trendingrepositories.data.remote.models.TrendingRepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingRepositoriesService {

    @GET("search/repositories?q=sort=stars&order=desc")
    suspend fun search(
        @Query("created") date: String,
        @Query("page") page: Int
    ): Response<TrendingRepositoryResponse>
}