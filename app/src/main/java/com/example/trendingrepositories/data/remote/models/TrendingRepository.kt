package com.example.trendingrepositories.data.remote.models

import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem
import com.example.trendingrepositories.utils.DateUtils
import com.google.gson.annotations.SerializedName
import java.util.Locale

data class TrendingRepository(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: RepositoryOwner,
    @SerializedName("url")
    val url: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("watchers")
    val watchers: Int,
    @SerializedName("forks")
    val forks: Int,
) {

    private val repositoryName: String
        get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    fun toRepositoryItem(): TrendingRepositoryItem {
        return TrendingRepositoryItem(
            id = id,
            name = repositoryName,
            description = description,
            author = owner.authorName,
            avatar = owner.avatar,
            watchers = watchers,
            forks = forks,
            language = language,
            createdAt = DateUtils.formatDate(
                date = DateUtils.parseDate(createdAt),
                format = DateUtils.SERVER_DATE_FORMAT
            )
        )
    }
}
