package com.github.beetv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.beetv.data.model.ItemGroup
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.PosterGridViewStyle

@Composable
fun PosterGrid(
    itemGroups: List<ItemGroup>,
    style: PosterGridViewStyle,
    modifier: Modifier = Modifier,
    onClickItem: (Item) -> Unit
) {
    LazyColumn(modifier) {
        items(itemGroups) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    it.title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
                AdaptiveGrid(minWidth = style.thumbWidth.dp) {
                    it.items.forEach {
                        Poster(it, style, onClickItem)
                    }
                }
            }
        }
    }
}

@Composable
fun Poster(item: Item, style: PosterGridViewStyle, onClick: (Item) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(4.dp))
        .clickable { onClick(item) }
        .padding(16.dp)
    ) {
        AsyncImage(
            model = item.thumbnail,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(style.thumbAspectRatio)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = item.title,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (item.desc.isNotBlank()) {
            Text(
                text = item.desc,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
