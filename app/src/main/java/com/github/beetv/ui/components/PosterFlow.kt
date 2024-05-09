package com.github.beetv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.beetv.data.model.GroupedItems
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.ViewStyle

@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)
fun PosterFlow(
    groupedItemsList: List<GroupedItems>,
    viewStyle: ViewStyle,
    onClick: (Item) -> Unit
) {
    LazyColumn {
        items(groupedItemsList) {
            Column(Modifier.fillMaxSize()) {
                // title
                Text(
                    it.title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                // poster grid
                //        LazyVerticalGrid(
                //            columns = GridCells.Adaptive(minSize = 210.dp),
                //            modifier = Modifier.fillMaxSize()
                //        ) {
                //            items(posterItems, PosterItem::id) {
                //                Item(it, onClick)
                //            }
                //        }
                FlowRow(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    it.items.forEach {
                        Poster(it, viewStyle, onClick)
                    }
                }
            }
        }
    }
}

@Composable
fun Poster(item: Item, viewStyle: ViewStyle, onClick: (Item) -> Unit) {
    Column(modifier = Modifier
        .width(viewStyle.thumbnailWidth.dp)
        .clip(RoundedCornerShape(4.dp))
        .clickable { onClick(item) }
        .padding(8.dp)
    ) {
        AsyncImage(
            model = item.thumbnail,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(viewStyle.thumbnailAspectRatio)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Spacer(Modifier.height(8.dp))
        Text(item.title, color = MaterialTheme.colorScheme.onPrimaryContainer)
        Text(
            item.desc,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
