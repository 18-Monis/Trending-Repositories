package com.example.trendingrepositories.data.remote.intercepters

import com.example.myapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TrendingRepositoriesHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION, BuildConfig.GITHUB_TOKEN)
            .addHeader(CLIENT_ID, BuildConfig.GITHUB_TOKEN)
            .addHeader(CLIENT_SECRET, BuildConfig.GITHUB_TOKEN)
            .build()
        return chain.proceed(request = request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val CLIENT_ID = "client_id"
        private const val CLIENT_SECRET = "client_secret"

    }
}