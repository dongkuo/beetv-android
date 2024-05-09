package com.github.beetv.ui.components

import android.util.Log
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

enum class Position {
    TOP,
    CENTER,
    Bottom
}

data class SideBarItem(
    val id: String,
    val icon: Int,
    val title: String,
    val position: Position
)

@Composable
fun SideBar(
    selectedIndex: Int,
    sideBarItems: List<SideBarItem>,
    onItemClick: (Int, SideBarItem) -> Unit
) {
    // group by position
    val sideBarItemGroupMap = sideBarItems.groupBy(SideBarItem::position)
    Log.i("SideBar", "sideBarItems=$sideBarItems, sideBarItemGroupMap=$sideBarItemGroupMap")
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxHeight()
    ) {
        // Top
        Spacer(Modifier.height(4.dp))
        ItemGroup(selectedIndex, sideBarItemGroupMap[Position.TOP], sideBarItems, onItemClick)
        // Center
        Spacer(Modifier.height(16.dp))
        ItemGroup(selectedIndex, sideBarItemGroupMap[Position.CENTER], sideBarItems, onItemClick)
        // Bottom
        ItemGroup(selectedIndex, sideBarItemGroupMap[Position.Bottom], sideBarItems, onItemClick)
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun ItemGroup(
    selectedIndex: Int,
    groupItems: List<SideBarItem>?,
    totalItems: List<SideBarItem>,
    onItemClick: (Int, SideBarItem) -> Unit
) {
    if (!groupItems.isNullOrEmpty()) {
        Column {
            groupItems.forEach {
                Poster(
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
private fun Poster(
    selected: Boolean,
    isLastInGroup: Boolean,
    index: Int,
    sideBarItem: SideBarItem,
    onItemClick: (Int, SideBarItem) -> Unit
) {
    NavigationRailItem(
        label = { Text(sideBarItem.title) },
        icon = {
            Icon(
                painter = painterResource(sideBarItem.icon),
                sideBarItem.title,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(28.dp)
            )
        },
        selected = selected,
        onClick = { onItemClick(index, sideBarItem) }
    )
    if (!isLastInGroup) {
        Spacer(Modifier.height(8.dp))
    }
}
