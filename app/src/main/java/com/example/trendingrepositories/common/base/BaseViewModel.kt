package com.example.trendingrepositories.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trendingrepositories.common.Result
import com.example.trendingrepositories.common.Result.Loading.doOnFailure
import com.example.trendingrepositories.common.Result.Loading.doOnLoading
import com.example.trendingrepositories.common.Result.Loading.doOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.UnknownHostException

open class BaseViewModel :
    ViewModel() {

    fun <T> execute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        viewModelScope.launch() {
            try {
                call().also(onSuccess)
            } catch (e: UnknownHostException) {
                onError(e)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected suspend fun <T> execute(
        flow: Flow<Result<T>>,
        onLoading: (() -> Unit)? = null,
        onSuccess: (T) -> Unit,
        onError: (Throwable?) -> Unit
    ) {
        try {
            flow
                .doOnLoading { onLoading?.invoke() }
                .doOnSuccess { data -> onSuccess.invoke(data) }
                .doOnFailure { onError.invoke(it) }
                .collect()
        } catch (e: UnknownHostException) {
            onError(e)
        } catch (e: Exception) {
            onError(e)
        }
    }

    fun <T : Any> executePaging(
        call: Flow<PagingData<T>>,
        onLoading: (() -> Unit)? = null,
        onSuccess: (PagingData<T>) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        viewModelScope.launch {
            onLoading?.invoke()
            try {
                val result = call.cachedIn(viewModelScope)
                result.collect { data ->
                    onSuccess(data)
                }
            } catch (e: UnknownHostException) {
                onError(e)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}