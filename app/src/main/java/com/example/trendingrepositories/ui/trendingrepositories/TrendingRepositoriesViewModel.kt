package com.example.trendingrepositories.ui.trendingrepositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.example.trendingrepositories.common.base.BaseViewModel
import com.example.trendingrepositories.data.local.repositories.FavouriteRepositoriesManager
import com.example.trendingrepositories.data.remote.repositories.TrendingRepositoriesManager
import com.example.trendingrepositories.ui.home.DateType
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem
import com.example.trendingrepositories.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TrendingRepositoriesViewModel @Inject constructor(
    private val trendingRepositoriesManager: TrendingRepositoriesManager,
    private val favouriteRepositoriesManager: FavouriteRepositoriesManager
) : BaseViewModel() {

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _trendingRepositoriesItemsLiveData =
        MutableLiveData<PagingData<TrendingRepositoryItem>>()
    val trendingRepositoriesItemsLiveData: LiveData<PagingData<TrendingRepositoryItem>> =
        _trendingRepositoriesItemsLiveData

    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorLiveData: LiveData<Boolean> = _errorLiveData

    private var trendingRepositoriesItems: PagingData<TrendingRepositoryItem> = PagingData.empty()
    private val favouriteRepositoriesItems: MutableList<TrendingRepositoryItem> = mutableListOf()

    private var dateType: DateType = DateType.LAST_DAY

    private val currentDate: Date
        get() = Date()

    init {
        loadFavouriteRepositories()
    }

    fun setDateType(dateType: DateType) {
        this.dateType = dateType
    }

    fun loadTrendingRepositories() {
        executePaging(
            call = trendingRepositoriesManager.getTrendingRepositories(getDate()),
            onLoading = {
                showLoading()
                hideError()
            },
            onSuccess = { data ->
                val items = data.map { it.toRepositoryItem() }
                _trendingRepositoriesItemsLiveData.postValue(items.mapSelectedRepositoriesFromLocal())
                trendingRepositoriesItems = items
                hideLoading()
            },
            onError = { error ->
                showError()
                hideLoading()
            }
        )
    }

    fun handlePassengersListState(loadState: CombinedLoadStates, itemCount: Int) {
        val currentState = loadState.source.refresh

        _isLoadingLiveData.value = currentState is LoadState.Loading
        _errorLiveData.value = currentState is LoadState.Error


        // handle empty data
        if (currentState is LoadState.NotLoading &&
            loadState.append.endOfPaginationReached &&
            itemCount < 1
        ) {
            // should create empty live data to indicate if the data is empty or not instead to show error.
            showError()
        } else {
            hideError()
        }
    }

    fun addRepositoryToFavourite(item: TrendingRepositoryItem) {
        viewModelScope.launch {
            val newItem = item.toTrendingRepositoryEntity()
            favouriteRepositoriesManager.addRepository(newItem)
            updateSelectedItem(item = item, isSelected = true)
        }
    }

    fun deletedRepositoryFromFavourite(item: TrendingRepositoryItem) {
        viewModelScope.launch {
            favouriteRepositoriesManager.deleteRepository(item.id)
            updateSelectedItem(item = item, isSelected = false)
        }
    }

    fun search(query: String) {
        if (query.isEmpty() || query.isBlank()) {
            _trendingRepositoriesItemsLiveData.value = trendingRepositoriesItems
            return
        }
        val filteredItems = trendingRepositoriesItems.filter { item ->
            item.name.contains(query, ignoreCase = true)
        }
        _trendingRepositoriesItemsLiveData.value = filteredItems
    }

    fun onTrendingRepositoryDataChanged() {
        loadFavouriteRepositories {
            _trendingRepositoriesItemsLiveData.value =
                trendingRepositoriesItems.mapSelectedRepositoriesFromLocal()
        }
    }

    private fun loadFavouriteRepositories(onFavouriteRepositoriesLoaded: (() -> Unit)? = null) {
        viewModelScope.launch {
            favouriteRepositoriesItems.clear()
            favouriteRepositoriesManager.getFavouriteRepositories().collect { entities ->
                val items = entities.map { entity -> TrendingRepositoryItem.from(entity) }
                favouriteRepositoriesItems.addAll(items)
                onFavouriteRepositoriesLoaded?.invoke()
            }
        }
    }

    private fun updateSelectedItem(item: TrendingRepositoryItem, isSelected: Boolean) {
        _trendingRepositoriesItemsLiveData.value = _trendingRepositoriesItemsLiveData.value?.map {
            if (item.id == it.id) {
                it.copy(isSelected = isSelected)
            } else {
                it
            }
        }
    }

    private fun showLoading() {
        _isLoadingLiveData.postValue(true)
    }

    private fun hideLoading() {
        _isLoadingLiveData.postValue(false)
    }

    private fun showError() {
        _errorLiveData.postValue(true)
    }

    private fun hideError() {
        _errorLiveData.postValue(false)
    }

    private fun PagingData<TrendingRepositoryItem>.mapSelectedRepositoriesFromLocal(): PagingData<TrendingRepositoryItem> {
        if (favouriteRepositoriesItems.isEmpty()) {
            return this.map { it.copy(isSelected = false) }
        }

        return map { item ->
            val favouriteItem =
                favouriteRepositoriesItems.find { favouriteItem -> favouriteItem.id == item.id }
            if (favouriteItem != null) {
                item.copy(isSelected = favouriteItem.isSelected)
            } else {
                item
            }
        }
    }

    private fun getDate(): Date {
        return when (dateType) {
            DateType.LAST_DAY -> currentDate
            DateType.LAST_WEEK -> DateUtils.getLastWeekDate(currentDate)
            DateType.LAST_MONTH -> DateUtils.getLastMonthDate(currentDate)
        }
    }
}
