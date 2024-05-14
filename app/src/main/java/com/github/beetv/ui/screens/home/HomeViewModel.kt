package com.github.beetv.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.GroupedItems
import com.github.beetv.data.model.noneChannel
import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.data.repository.ContentRepository
import com.github.beetv.ui.components.SideBarItem
import com.github.beetv.ui.components.TabBarItem
import com.github.beetv.ui.data.SIDE_BAR_ITEMS
import com.github.beetv.ui.data.mockChannels

class HomeViewModel(private val channelRepository: ChannelRepository) : ViewModel() {

    private val noneTabBarItem = TabBarItem(0, "", noneChannel)

    // side bar
    var selectedSideIndex by mutableIntStateOf(1)
        private set
    var sideBarItems: List<SideBarItem> by mutableStateOf(emptyList())
        private set

    // tab bar
    var selectedTabIndex by mutableIntStateOf(0)
        private set
    var tabBarItems: List<TabBarItem<Channel>> by mutableStateOf(emptyList())
        private set

    var groupItems: List<GroupedItems> by mutableStateOf(emptyList())
        private set

    var isFirstLoading: Boolean by mutableStateOf(true)

    var isLoading: Boolean by mutableStateOf(true)

    fun onSideSelect(index: Int, sideBarItem: SideBarItem) {
        selectedSideIndex = index
    }

    fun onTabSelect(index: Int, tabBarItem: TabBarItem<Channel>) {
        selectedTabIndex = index
    }

    fun currentChannel() = tabBarItems.getOrElse(selectedTabIndex) { noneTabBarItem }.data

    suspend fun loadSideBarItems() {
        sideBarItems = SIDE_BAR_ITEMS
    }

    suspend fun loadTabBarItems() {
        tabBarItems = channelRepository.listChannel().map { TabBarItem(it.icon, it.title, it) }
//        channelRepository.saveAll(*mockChannels.toTypedArray())
    }

    suspend fun loadGroupItems() {
        isLoading = true
        groupItems = ContentRepository.resolve(currentChannel()).groupContent()
        isFirstLoading = false
        isLoading = false
    }

    class ViewModelFactory(private val repository: ChannelRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
    }
}