package com.github.beetv.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Tabs(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabPanesBuilder: TabPanes.() -> Unit
) {
    val tabPanes = TabPanes().apply(tabPanesBuilder)
    if (tabPanes.items.isNotEmpty()) {
        Column(modifier) {
            // tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabPanes.items.forEachIndexed { index, tabPane ->
                    Tab(
                        selected = index == selectedTabIndex,
                        text = tabPane.title,
                        onClick = { tabPane.onClick(index) }
                    )
                }
            }
            // pane
            tabPanes.items[selectedTabIndex].content()
        }
    }
}

data class TabPane(
    val title: @Composable () -> Unit,
    val onClick: (Int) -> Unit,
    val content: @Composable () -> Unit
)

class TabPanes {
    val items: MutableList<TabPane> = mutableListOf()

    fun items() {

    }

    fun tabPane(
        title: @Composable () -> Unit,
        onClick: (Int) -> Unit = {},
        content: @Composable () -> Unit
    ) {
        this.items.add(TabPane(title, onClick, content))
    }

    fun tabPane(
        textTitle: String,
        onClick: (Int) -> Unit = {},
        content: @Composable () -> Unit
    ) {
        tabPane(title = { Text(text = textTitle) }, onClick, content)
    }
}