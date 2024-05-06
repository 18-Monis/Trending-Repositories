package com.example.trendingrepositories.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.toLiveData(): LiveData<T> = liveData {
    collect {
        emit(it)
    }
}