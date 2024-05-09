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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class TabBarItem<T>(val icon: Int, val title: String, val data: T)


@Composable
fun <T> TabBar(
    selectedTabIndex: Int,
    tabs: List<TabBarItem<T>>,
    onItemClick: (Int, TabBarItem<T>) -> Unit,
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
    tabs: List<TabBarItem<T>>,
    onClick: (Int, TabBarItem<T>) -> Unit
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
                    painter = painterResource(item.icon),
                    contentDescription = item.title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            })
    }
}
