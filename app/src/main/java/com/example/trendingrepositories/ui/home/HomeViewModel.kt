package com.example.trendingrepositories.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trendingrepositories.common.Event
import com.example.trendingrepositories.common.base.BaseViewModel
import com.example.trendingrepositories.common.network.ConnectivityObserver
import com.example.trendingrepositories.common.network.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val connectivityObserver: ConnectivityObserver) :
    BaseViewModel() {

    private val _dateTypeLiveData = MutableLiveData<DateType>(DateType.LAST_DAY)
    val dateTypeLiveData: LiveData<DateType> = _dateTypeLiveData

    private val _searchQueryLiveData = MutableLiveData<String>()
    val searchQueryLiveData: LiveData<String> = _searchQueryLiveData

    private val _showFilterLiveData = MutableLiveData<Boolean>()
    val showFilterLiveData: LiveData<Boolean> = _showFilterLiveData

    private val _showSearchViewLiveData = MutableLiveData<Boolean>()
    val showSearchViewLiveData: LiveData<Boolean> = _showSearchViewLiveData

    private val _favouriteRepositoriesItemChangedEvent = MutableLiveData<Boolean>()
    val favouriteRepositoriesItemChangedEvent: LiveData<Boolean> =
        _favouriteRepositoriesItemChangedEvent

    private val _connectivityStatusEvent = MutableLiveData<Event<Status>>()
    val connectivityStatusEvent: LiveData<Event<Status>> = _connectivityStatusEvent

    val dateType: DateType
        get() = _dateTypeLiveData.value ?: DateType.LAST_DAY

    val showSearchView: Boolean
        get() = _showSearchViewLiveData.value ?: false

    val showFilterView: Boolean
        get() = _showFilterLiveData.value ?: false

    init {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                _connectivityStatusEvent.postValue(Event(status))
            }
        }
    }

    fun setDateType(dateType: DateType) {
        _dateTypeLiveData.value = dateType
    }

    fun setShowMenuItems(showSearchView: Boolean, showFilterView: Boolean) {
        _showSearchViewLiveData.value = showFilterView
        _showFilterLiveData.value = showFilterView
    }

    fun notifyDataChanged() {
        _favouriteRepositoriesItemChangedEvent.value = true
    }

    fun setSearchQuery(query: String) {
        _searchQueryLiveData.value = query.trim()
    }
}