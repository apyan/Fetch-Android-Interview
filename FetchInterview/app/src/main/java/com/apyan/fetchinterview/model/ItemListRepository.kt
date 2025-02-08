package com.apyan.fetchinterview.model

import com.apyan.fetchinterview.model.response.ItemListResponse
import javax.inject.Inject

class ItemListRepository @Inject constructor(
    private val itemListService: ItemListService
) {
    suspend fun loadItemList(): ItemListResponse {
        return itemListService.loadLocationSearchInfo()
    }
}