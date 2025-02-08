package com.apyan.fetchinterview.ui.element

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apyan.fetchinterview.model.response.Item

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    item: Item = Item()
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 15.dp,
                    bottom = 15.dp
                )
        ) {
            item.name?.let {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = it,
                    fontWeight = FontWeight.Bold
                )
            }

            item.id?.let {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "ID: " + it.toString()
                )
            }

            item.listId?.let {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "List ID: " + it.toString()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCardPreview() {
    ItemCard(
        item = Item(
            id = 123,
            listId = 5,
            name = "Test"
        )
    )
}