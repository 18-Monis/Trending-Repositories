package com.example.trendingrepositories.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "trending_repositories", indices = [Index(value = ["name"])])
data class TrendingRepositoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val repositoryId: Long,
    val name: String,
    val description: String,
    val avatar: String,
    val author: String,
    val createdAt: String,
    val language:String?,
    val forks: Int,
    val watchers: Int,
    val isAddedToFavourite: Boolean = true
)