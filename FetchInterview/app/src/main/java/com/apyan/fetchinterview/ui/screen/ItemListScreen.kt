package com.apyan.fetchinterview.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apyan.fetchinterview.model.response.Item
import com.apyan.fetchinterview.ui.card.GroupCard

@Composable
fun ItemListScreen(
    itemMapListing: HashMap<Int, MutableList<Item>> = hashMapOf()
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState
    ) {
        items(itemMapListing.toList()) { itemGroup ->
            GroupCard(
                listID = itemGroup.first,
                itemList = itemGroup.second
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemListScreenPreview() {
    ItemListScreen()
}