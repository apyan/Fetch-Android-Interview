package com.apyan.fetchinterview.model

import com.apyan.fetchinterview.model.response.Item
import javax.inject.Inject

class ItemListRepository @Inject constructor(
    private val itemListService: ItemListService
) {
    suspend fun loadItemList(): List<Item> {
        return itemListService.loadLocationSearchInfo()
    }
}