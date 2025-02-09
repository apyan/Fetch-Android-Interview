package com.apyan.fetchinterview.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apyan.fetchinterview.R
import com.apyan.fetchinterview.model.response.Item

@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    listID: Int = 0,
    itemList: MutableList<Item> = mutableListOf()
) {
    Card(
        modifier = modifier
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 5.dp,
                        horizontal = 25.dp
                    ),
                text = String.format(stringResource(id = R.string.list_id_label), listID),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            itemList.forEach { item ->
                ItemCard(
                    item = item
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCardPreview() {
    GroupCard(
        listID = 5,
        itemList = mutableListOf(
            Item(
                id = 123,
                listId = 5,
                name = "Test"
            ),
        )
    )
}