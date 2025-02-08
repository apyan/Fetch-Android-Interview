package com.apyan.fetchinterview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apyan.fetchinterview.helper.ConnectionHelper
import com.apyan.fetchinterview.model.ItemListRepository
import com.apyan.fetchinterview.model.response.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
    private val itemListRepository: ItemListRepository,
) : ViewModel() {

    private val _loadingStatus: MutableStateFlow<LoadingStatus> = MutableStateFlow(LoadingStatus.LOADING)
    val loadingStatus = _loadingStatus.asStateFlow()

    private val _itemList: MutableStateFlow<List<Item>?> = MutableStateFlow(emptyList())
    val itemList = _itemList.asStateFlow()

    private var itemListJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        itemListJob?.cancel()
        itemListJob = null
    }

    fun launchItemListSearch(context: Context) {
        itemListJob?.cancel()
        if (ConnectionHelper.isConnected(context) && !ConnectionHelper.isAirplaneModeOn(context)) {
            itemListJob = viewModelScope.launch(Dispatchers.IO) {
                getItemListing()
            }
        } else {
            _loadingStatus.value = LoadingStatus.ERROR
        }
    }

    private suspend fun getItemListing() {
        _loadingStatus.value = LoadingStatus.LOADING
        try {
            val itemListResult = itemListRepository.loadItemList()
            _itemList.value = itemListResult.itemListing
            _loadingStatus.value = LoadingStatus.SUCCESS
        } catch (_: HttpException) {
            _loadingStatus.value = LoadingStatus.ERROR
        }
    }
}

enum class LoadingStatus {
    LOADING,
    SUCCESS,
    ERROR
}