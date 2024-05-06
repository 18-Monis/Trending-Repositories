package com.example.trendingrepositories.data.remote.models

import com.google.gson.annotations.SerializedName

data class TrendingRepositoryResponse(
    @SerializedName("total_count")
    val totalCount: Long,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<TrendingRepository>,
)
