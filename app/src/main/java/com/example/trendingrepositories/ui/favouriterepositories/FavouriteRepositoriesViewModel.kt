package com.example.trendingrepositories.ui.favouriterepositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trendingrepositories.common.base.BaseViewModel
import com.example.trendingrepositories.data.local.repositories.FavouriteRepositoriesManager
import com.example.trendingrepositories.ui.trendingrepositories.models.TrendingRepositoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteRepositoriesViewModel @Inject constructor(
    private val favouriteRepositoriesManager: FavouriteRepositoriesManager
) : BaseViewModel() {

    private val _isEmptyLiveData = MediatorLiveData<Boolean>()
    val isEmptyLiveData: LiveData<Boolean> = _isEmptyLiveData

    private val _favouriteRepositoriesItemsLiveData =
        MutableLiveData<List<TrendingRepositoryItem>>()
    val favouriteRepositoriesItemsLiveData: LiveData<List<TrendingRepositoryItem>> =
        _favouriteRepositoriesItemsLiveData

    private val favouriteRepositoriesItems: MutableList<TrendingRepositoryItem> = mutableListOf()

    init {
        loadFavouriteRepositories()
        _isEmptyLiveData.addSource(_favouriteRepositoriesItemsLiveData) { items ->
            _isEmptyLiveData.value = items.isEmpty()
        }
    }

    private fun loadFavouriteRepositories() {
        viewModelScope.launch {
            favouriteRepositoriesItems.clear()
            favouriteRepositoriesManager.getFavouriteRepositories().collect { entities ->
                val items = entities.map { entity ->
                    TrendingRepositoryItem.from(
                        entity
                    )
                }
                favouriteRepositoriesItems.addAll(items)
                _favouriteRepositoriesItemsLiveData.postValue(items)
            }
        }
    }

    fun search(query: String) {
        val currentItems = _favouriteRepositoriesItemsLiveData.value
        if (query.isEmpty() || query.isBlank()) {
            _favouriteRepositoriesItemsLiveData.value = currentItems.orEmpty()
            return
        }
        val filteredItems = favouriteRepositoriesItems.filter { item ->
            item.name.contains(query, ignoreCase = true)
        }
        _favouriteRepositoriesItemsLiveData.value = filteredItems
    }

    fun deletedRepositoryFromFavourite(item: TrendingRepositoryItem) {
        viewModelScope.launch {
            favouriteRepositoriesManager.deleteRepository(item.id)
            removeItem(item)
        }
    }

    private fun removeItem(item: TrendingRepositoryItem) {
        _favouriteRepositoriesItemsLiveData.value =
            favouriteRepositoriesItems.filter { item.id != it.id }
    }
}