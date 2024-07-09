package com.github.beetv.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.beetv.data.model.FileExplorerViewStyle
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.PosterGridViewStyle
import com.github.beetv.ui.components.LoadingIndicator
import com.github.beetv.ui.components.PosterGrid
import com.github.beetv.ui.components.TabBar
import com.github.beetv.ui.data.Routes

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadChannels()
    }
    // Right
    Column {
        // Top: TabBar
        TabBar(
            viewModel.selectedTabIndex,
            viewModel.tabItems
        ) { i, _ -> viewModel.selectTab(i) }
        // Main
        // Content
        val state by viewModel.state.collectAsStateWithLifecycle()
        Box {
            if (state.isEmpty) {
                EmptyView()
            } else {
                ContentView(state) {
                    navHostController.navigate(Routes.detailPage(state.channel.id, it.id))
                }
            }
            // Loading
            if (state.isLoading) {
                LoadingIndicator()
            }
        }
    }
}

@Composable
fun EmptyView() {
    Text(text = "暂无内容")
}

@Composable
fun ContentView(state: HomeUiState, onClickItem: (Item) -> Unit) {
    when (state.viewStyle) {
        is PosterGridViewStyle -> PosterGrid(
            state.itemGroups,
            state.viewStyle,
            onClickItem = onClickItem
        )

        is FileExplorerViewStyle -> FileExplorerView(state)
    }
}

@Composable
fun FileExplorerView(state: HomeUiState) {
    Text(text = "FileExplorerView")
}
