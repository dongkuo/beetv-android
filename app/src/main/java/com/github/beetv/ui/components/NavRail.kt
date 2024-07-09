package com.github.beetv.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


data class NavRailItem(
    val icon: Int,
    val title: String,
    val position: Position,
    val route: String? = null,
) {
    enum class Position {
        TOP,
        CENTER,
        BOTTOM
    }
}

@Composable
fun NavRail(
    selectedIndex: Int,
    navRailItems: List<NavRailItem>,
    onItemClick: (Int, NavRailItem) -> Unit
) {
    // group by position
    val navRailItemGroupMap = navRailItems.groupBy(NavRailItem::position)
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxHeight()
    ) {
        // Top
        Spacer(Modifier.height(4.dp))
        ItemGroup(
            selectedIndex,
            navRailItemGroupMap[NavRailItem.Position.TOP],
            navRailItems,
            onItemClick
        )
        // Center
        Spacer(Modifier.height(16.dp))
        ItemGroup(
            selectedIndex,
            navRailItemGroupMap[NavRailItem.Position.CENTER],
            navRailItems,
            onItemClick
        )
        // Bottom
        ItemGroup(
            selectedIndex,
            navRailItemGroupMap[NavRailItem.Position.BOTTOM],
            navRailItems,
            onItemClick
        )
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun ItemGroup(
    selectedIndex: Int,
    groupItems: List<NavRailItem>?,
    totalItems: List<NavRailItem>,
    onItemClick: (Int, NavRailItem) -> Unit
) {
    if (!groupItems.isNullOrEmpty()) {
        Column {
            groupItems.forEach {
                Item(
                    totalItems[selectedIndex] == it,
                    groupItems.last() == it,
                    totalItems.indexOf(it),
                    it,
                    onItemClick
                )
            }
        }
    }
}

@Composable
private fun Item(
    selected: Boolean,
    isLastInGroup: Boolean,
    index: Int,
    navRailItem: NavRailItem,
    onItemClick: (Int, NavRailItem) -> Unit
) {
    NavigationRailItem(
        label = { Text(navRailItem.title) },
        icon = {
            Icon(
                painter = painterResource(navRailItem.icon),
                navRailItem.title,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(28.dp)
            )
        },
        selected = selected,
        onClick = { onItemClick(index, navRailItem) }
    )
    if (!isLastInGroup) {
        Spacer(Modifier.height(8.dp))
    }
}
