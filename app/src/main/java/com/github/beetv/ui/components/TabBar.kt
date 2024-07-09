package com.github.beetv.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.beetv.ext.painter

data class TabItem<T>(val icon: String, val title: String, val data: T)


@Composable
fun <T> TabBar(
    selectedTabIndex: Int,
    tabs: List<TabItem<T>>,
    onItemClick: (Int, TabItem<T>) -> Unit,
) {
    if (tabs.size <= 6) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(selectedTabIndex, tabs, onItemClick)
        }
    } else {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp,
        ) {
            Tab(selectedTabIndex, tabs, onItemClick)
        }
    }
}

@Composable
private fun <T> Tab(
    selectedTabIndex: Int,
    tabs: List<TabItem<T>>,
    onClick: (Int, TabItem<T>) -> Unit
) {
    tabs.forEachIndexed { index, item ->
        val color = if (selectedTabIndex == index)
            MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        LeadingIconTab(
            selected = selectedTabIndex == index,
            onClick = { onClick(index, item) },
            text = {
                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(8.dp),
                    color = color
                )
            },
            icon = {
                Icon(
                    painter = painter(item.icon),
                    contentDescription = item.title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            })
    }
}
