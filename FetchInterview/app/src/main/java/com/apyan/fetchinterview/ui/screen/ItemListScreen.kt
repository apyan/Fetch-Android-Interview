package com.apyan.fetchinterview.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apyan.fetchinterview.model.response.Item
import com.apyan.fetchinterview.ui.card.GroupCard

@Composable
fun ItemListScreen(
    itemMapListing: HashMap<Int, MutableList<Item>> = hashMapOf()
) {
    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            itemMapListing.keys.forEach { key ->
                itemMapListing[key]?.let {
                    GroupCard(
                        listID = key,
                        itemList = it
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemListScreenPreview() {
    ItemListScreen()
}