package com.github.beetv.data.repository.content

import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ItemGroup
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.ItemDetail
import com.github.beetv.data.model.Page

interface ChannelContentRepository {
    suspend fun groupContent(channel: Channel): List<ItemGroup>
    suspend fun listContent(pageNum: Long, pageSize: Long, channel: Channel): Page<Item>
    suspend fun detailItem(id: String, channel: Channel): ItemDetail
    suspend fun resolveUrl(url: String, channel: Channel): String
}