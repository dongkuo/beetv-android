package com.github.beetv.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ItemGroup
import com.github.beetv.data.model.ViewStyle
import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.data.repository.content.DispatcherChannelContentRepository
import com.github.beetv.ui.components.TabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val channelRepository: ChannelRepository,
    private val contentRepository: DispatcherChannelContentRepository
) : ViewModel() {

    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    // tab bar
    var selectedTabIndex by mutableIntStateOf(0)
        private set

    var tabItems: List<TabItem<Channel>> by mutableStateOf(emptyList())
        private set

    var groupItems: List<ItemGroup> by mutableStateOf(emptyList())
        private set


    suspend fun loadChannels() {
        _state.update { it.copy(isLoading = true) }
        tabItems = channelRepository.listChannel().map { TabItem(it.icon, it.title, it) }
        if (tabItems.isEmpty()) {
            _state.update { HomeUiState(false) }
        } else {
            selectTab(0)
        }
    }

    fun selectTab(index: Int) {
        if (index < 0 || index >= tabItems.size) {
            return
        }
        this.selectedTabIndex = index
        _state.update { it.copy(channel = currentChannel(), itemGroups = emptyList()) }
        viewModelScope.launch {
            loadItemGroups()
        }
    }

    suspend fun loadItemGroups() {
        _state.update { it.copy(isLoading = true) }
        groupItems = contentRepository.groupContent(currentChannel())
        _state.update { it.copy(isLoading = false, itemGroups = groupItems) }
    }

    private fun currentChannel(): Channel =
        tabItems.getOrNull(selectedTabIndex)?.data ?: Channel.NONE

}

data class HomeUiState(
    val isLoading: Boolean = false,
    val channel: Channel = Channel.NONE,
    val itemGroups: List<ItemGroup> = emptyList(),
    val viewStyle: ViewStyle = ViewStyle.DEFAULT,
) {
    val isEmpty: Boolean = itemGroups.isEmpty()
}