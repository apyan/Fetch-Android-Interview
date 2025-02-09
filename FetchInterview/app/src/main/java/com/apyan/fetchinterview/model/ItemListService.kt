package com.apyan.fetchinterview.model

import com.apyan.fetchinterview.model.response.Item
import retrofit2.http.GET

interface ItemListService {

    @GET("hiring.json")
    suspend fun loadLocationSearchInfo(): List<Item>
}