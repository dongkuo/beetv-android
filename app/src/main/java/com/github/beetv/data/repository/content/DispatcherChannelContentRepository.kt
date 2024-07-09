package com.github.beetv.data.repository.content

import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ChannelType
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.ItemDetail
import com.github.beetv.data.model.ItemGroup
import com.github.beetv.data.model.Page
import com.github.beetv.data.model.emptyPage
import com.github.beetv.data.repository.content.spider.SpiderRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DispatcherChannelContentRepository @Inject constructor(
    private val spiderContentRepository: SpiderRepository,
    private val localhostContentRepository: LocalhostRepository,
    private val aliPanRepository: AliPanRepository,
    private val aListRepository: AListRepository,
) : ChannelContentRepository {

    private object EmptyRepository : ChannelContentRepository {
        override suspend fun groupContent(channel: Channel): List<ItemGroup> = emptyList()

        override suspend fun listContent(
            pageNum: Long,
            pageSize: Long,
            channel: Channel
        ): Page<Item> = emptyPage()

        override suspend fun detailItem(id: String, channel: Channel): ItemDetail =
            ItemDetail.EMPTY_ITEM_DETAIL

        override suspend fun resolveUrl(url: String, channel: Channel) = ""
    }

    private fun delegateContentRepository(channel: Channel): ChannelContentRepository {
        return when (channel.type) {
            ChannelType.SPIDER -> spiderContentRepository
            ChannelType.LOCALHOST -> localhostContentRepository
            ChannelType.ALI_PAN -> aliPanRepository
            ChannelType.A_LIST -> aListRepository
            else -> EmptyRepository
        }
    }

    override suspend fun groupContent(channel: Channel): List<ItemGroup> {
        return delegateContentRepository(channel).groupContent(channel)
    }

    override suspend fun listContent(pageNum: Long, pageSize: Long, channel: Channel): Page<Item> {
        return delegateContentRepository(channel).listContent(pageNum, pageSize, channel)
    }

    override suspend fun detailItem(id: String, channel: Channel): ItemDetail {
        return delegateContentRepository(channel).detailItem(id, channel)
    }

    override suspend fun resolveUrl(url: String, channel: Channel): String {
        return delegateContentRepository(channel).resolveUrl(url, channel)
    }
}