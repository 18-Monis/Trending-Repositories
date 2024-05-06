package com.example.trendingrepositories.common.network

import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {

    fun observe(): Flow<Status>
}