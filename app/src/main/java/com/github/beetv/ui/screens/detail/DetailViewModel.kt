package com.github.beetv.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ItemDetail
import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.data.repository.content.DispatcherChannelContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val channelRepository: ChannelRepository,
    private val contentRepository: DispatcherChannelContentRepository
) : ViewModel() {

    private var channel: Channel = Channel.NONE
    private val _state: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state

    fun loadDetail(channelId: Long, itemId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            channel = channelRepository.findById(channelId)
            val itemDetail = contentRepository.detailItem(itemId, channel)
            _state.update {
                it.copy(
                    isLoading = false,
                    itemDetail = itemDetail,
                    sourceGroupIndex = 0
                )
            }
        }
    }

    fun selectSourceGroup(index: Int) {
        _state.update { it.copy(sourceGroupIndex = index) }
    }

    suspend fun resolveMediaUrl(url: String): String {
        _state.update { it.copy(isLoading = true) }
        val mediaUrl = contentRepository.resolveUrl(url, channel)
        _state.update { it.copy(isLoading = false) }
        return mediaUrl
    }
}

data class DetailUiState(
    val isLoading: Boolean = false,
    val sourceGroupIndex: Int = -1,
    val itemDetail: ItemDetail = ItemDetail.EMPTY_ITEM_DETAIL
)
