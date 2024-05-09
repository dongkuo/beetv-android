package com.github.beetv.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.beetv.BeetvApplication
import com.github.beetv.data.model.GroupedItems
import com.github.beetv.data.model.ViewType
import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.data.repository.ContentRepository
import com.github.beetv.ui.components.PosterFlow
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
            // Bottom: Content
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
    val channel = viewModel.currentChannel()
    var pageNum: Long by remember { mutableLongStateOf(1) }
    var pageSize: Long by remember { mutableLongStateOf(10) }
    var data by remember { mutableStateOf(listOf<GroupedItems>()) }
    val contentRepository = remember { ContentRepository.resolve(channel) }
    LaunchedEffect(channel) {
        val page = contentRepository.groupContent(pageNum, pageSize)
        data += page.data
    }
    PosterFlow(data, channel.viewStyle) {}
}

@Composable
fun FileExplorerView(viewModel: HomeViewModel) {
    Text(text = "FileExplorerView")
}

@Composable
fun NoneChannelView() {
    Text(text = "NoneChannelView")
}
