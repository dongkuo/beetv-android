package com.github.beetv.data.repository.content

import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ItemGroup
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.ItemDetail
import com.github.beetv.data.model.Page
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AListRepository @Inject constructor() : ChannelContentRepository {
    override suspend fun groupContent(channel: Channel): List<ItemGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun listContent(pageNum: Long, pageSize: Long, channel: Channel): Page<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun detailItem(id: String, channel: Channel): ItemDetail {
        TODO("Not yet implemented")
    }

    override suspend fun resolveUrl(url: String, channel: Channel): String {
        TODO("Not yet implemented")
    }
}