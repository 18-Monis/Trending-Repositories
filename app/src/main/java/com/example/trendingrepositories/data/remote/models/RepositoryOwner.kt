package com.example.trendingrepositories.data.remote.models

import com.google.gson.annotations.SerializedName
import java.util.Locale

data class RepositoryOwner(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatar: String
){

    val authorName:String
        get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}