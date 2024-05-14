package com.github.beetv.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.beetv.BeetvApplication
import com.github.beetv.data.model.ViewType
import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.ui.components.LoadingIndicator
import com.github.beetv.ui.components.PosterGrid
import com.github.beetv.ui.components.SideBar
import com.github.beetv.ui.components.TabBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.ViewModelFactory(
            ChannelRepository((LocalContext.current.applicationContext as BeetvApplication).database.channelDao())
        )
    )
) {
    LaunchedEffect(Unit) {
        viewModel.loadSideBarItems()
        viewModel.loadTabBarItems()
    }
    Row {
        // Left
        SideBar(viewModel.selectedSideIndex, viewModel.sideBarItems, viewModel::onSideSelect)
        // Right
        Column {
            // Top: TabBar
            TabBar(viewModel.selectedTabIndex, viewModel.tabBarItems, viewModel::onTabSelect)
            // Main: Content
            when (viewModel.currentChannel().viewType) {
                ViewType.POSTER_FLOW -> PosterFlowView(viewModel)
                ViewType.FILE_EXPLORER -> FileExplorerView(viewModel)
                ViewType.NONE -> NoneChannelView()
            }
        }
    }
}

@Composable
fun PosterFlowView(viewModel: HomeViewModel) {
    if (viewModel.isFirstLoading) {
        LaunchedEffect(Unit) {
            viewModel.loadGroupItems()
        }
    }
    Box {
        PosterGrid(viewModel.groupItems, viewModel.currentChannel().viewStyle) {}
        if (viewModel.isLoading) {
            LoadingIndicator()
        }
    }
}

@Composable
fun FileExplorerView(viewModel: HomeViewModel) {
    Text(text = "FileExplorerView")
}

@Composable
fun NoneChannelView() {
    Text(text = "NoneChannelView")
}
