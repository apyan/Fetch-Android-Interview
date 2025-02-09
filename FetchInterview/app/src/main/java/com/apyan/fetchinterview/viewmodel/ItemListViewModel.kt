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

    private val _itemList: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())
    val itemList = _itemList.asStateFlow()

    private val _hashedItemMap: MutableStateFlow<HashMap<Int, MutableList<Item>>> = MutableStateFlow(hashMapOf())
    val hashedItemMap = _hashedItemMap.asStateFlow()

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
            _itemList.value = itemListResult

            val filteredListing = filterInvalidNames(itemListResult)
            _hashedItemMap.value = sortByListIdIntoMap(filteredListing)

            _itemList.value = filteredListing
            _loadingStatus.value = LoadingStatus.SUCCESS
        } catch (_: HttpException) {
            _loadingStatus.value = LoadingStatus.ERROR
        }
    }

    private fun filterInvalidNames(itemListing: List<Item>): List<Item> {
        return itemListing.filter { !it.name.isNullOrBlank() }
    }

    private fun sortByName(itemListing: MutableList<Item>) {
        itemListing.sortBy { it.name }
    }

    private fun sortByListIdIntoMap(itemListing: List<Item>): HashMap<Int, MutableList<Item>> {
        val organizedHashMap = HashMap<Int, MutableList<Item>>()

        // Sort the List ID into different lists in the HashMap
        itemListing.forEach { item ->
            if (organizedHashMap.contains(item.listId)) {
                val tempMutableList = organizedHashMap[item.listId]
                tempMutableList?.add(item)
                item.listId?.let {
                    tempMutableList?.let {
                        list -> organizedHashMap.put(it, list)
                    }
                }
            } else {
                item.listId?.let { itemId ->
                    organizedHashMap.put(itemId, mutableListOf(item))
                }
            }
        }
        organizedHashMap.toSortedMap()

        // For each List ID, sort the name by alphabetical order
        organizedHashMap.keys.forEach { key ->
            val tempMutableList = organizedHashMap[key]
            tempMutableList?.let {
                sortByName(tempMutableList)
                organizedHashMap[key] = it
            }
        }
        return organizedHashMap
    }
}

enum class LoadingStatus {
    LOADING,
    SUCCESS,
    ERROR
}