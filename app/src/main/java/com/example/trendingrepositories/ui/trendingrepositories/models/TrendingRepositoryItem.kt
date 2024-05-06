package com.example.trendingrepositories.ui.trendingrepositories.models

import android.os.Parcelable
import com.example.trendingrepositories.data.local.entities.TrendingRepositoryEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrendingRepositoryItem(
    val id: Long,
    val name: String,
    val author: String,
    val avatar: String,
    val description: String,
    val forks: Int,
    val language: String?,
    val watchers: Int,
    val createdAt: String,
    val isSelected: Boolean = false
) : Parcelable {

    companion object{
        fun from(entity: TrendingRepositoryEntity): TrendingRepositoryItem {
            return TrendingRepositoryItem(
                id = entity.repositoryId,
                name = entity.name,
                author = entity.author,
                description = entity.description,
                createdAt = entity.createdAt,
                avatar = entity.avatar,
                watchers = entity.watchers,
                language = entity.language,
                forks = entity.forks,
                isSelected = entity.isAddedToFavourite
            )
        }
    }

    fun toTrendingRepositoryEntity(): TrendingRepositoryEntity {
        return TrendingRepositoryEntity(
            repositoryId = id,
            name = name,
            description = description,
            author = author,
            avatar = avatar,
            createdAt = createdAt,
            watchers = watchers ,
            language = language,
            forks = forks
        )
    }
}