package com.github.beetv.data.repository.content.spider

import com.github.beetv.data.model.GroupedItems
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.Page
import com.github.beetv.data.repository.ContentRepository
import com.github.beetv.ui.data.randomItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpiderContentRepository(private val spiderConfig: SpiderConfig) : ContentRepository {

    init {

    }

    override suspend fun groupContent(pageNum: Long, pageSize: Long): Page<GroupedItems> =
        withContext(Dispatchers.IO) {
            Page(
                100, listOf(
                    GroupedItems("科幻", randomItems()),
                    GroupedItems("国产", randomItems()),
                    GroupedItems("美剧", randomItems()),
                    GroupedItems("韩剧", randomItems()),
                    GroupedItems("日剧", randomItems()),
                )
            )
        }

    override suspend fun listContent(pageNum: Long, pageSize: Long): Page<Item> {
        TODO("Not yet implemented")
    }
}