package com.example.trendingrepositories.common.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import com.example.trendingrepositories.common.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

open class BaseRepository {

    suspend fun <T> enqueueCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>,
    ): Flow<Result<T?>> = flow {
        emit(Result.Loading)
        val response = apiCall()
        if (response.isSuccessful) {
            emit(Result.Success(response.body()))
        } else {
            val error = response.errorBody()
            if (error != null) {
                emit(Result.Failure(IOException(error.toString())))
            } else {
                emit(Result.Failure(IOException("something went wrong")))
            }
        }
    }.catch { e ->
        e.printStackTrace()
        emit(Result.Failure(Exception(e)))
    }.flowOn(dispatcher)
}