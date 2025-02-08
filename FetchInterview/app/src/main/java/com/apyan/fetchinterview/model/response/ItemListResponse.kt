package com.apyan.fetchinterview.model.response

import com.google.gson.annotations.SerializedName

data class ItemListResponse(
    val itemListing: List<Item>? = null,
)

data class Item(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("listId")
    val listId: Int? = null,
    @SerializedName("name")
    val name: String? = null,
)