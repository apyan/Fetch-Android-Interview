package com.apyan.fetchinterview.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apyan.fetchinterview.R
import com.apyan.fetchinterview.model.response.Item

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    item: Item = Item()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 2.dp,
                horizontal = 20.dp
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        item.name?.let {
            Text(
                modifier = Modifier.padding(
                    vertical = 5.dp,
                    horizontal = 10.dp
                ),
                text = it,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }

        item.id?.let {
            Text(
                modifier = Modifier.padding(5.dp),
                text = String.format(stringResource(R.string.id_label), it),
                fontSize = 15.sp,
            )
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