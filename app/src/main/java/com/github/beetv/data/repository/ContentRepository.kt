package com.github.beetv.data.repository

import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ChannelType
import com.github.beetv.data.model.GroupedItems
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.Page
import com.github.beetv.data.repository.content.AListContentRepository
import com.github.beetv.data.repository.content.AliPanContentRepository
import com.github.beetv.data.repository.content.LocalhostContentRepository
import com.github.beetv.data.repository.content.spider.SpiderContentRepository

interface ContentRepository {
    suspend fun groupContent(): List<GroupedItems>
    suspend fun listContent(pageNum: Long, pageSize: Long): Page<Item>

    companion object {
        fun resolve(channel: Channel): ContentRepository {
            return when (channel.type) {
                ChannelType.SPIDER -> SpiderContentRepository(channel.deserializeConfig())

                ChannelType.LOCALHOST -> LocalhostContentRepository()
                ChannelType.ALI_PAN -> AliPanContentRepository()
                ChannelType.A_LIST -> AListContentRepository()
                else -> TODO()
            }
        }
    }
}