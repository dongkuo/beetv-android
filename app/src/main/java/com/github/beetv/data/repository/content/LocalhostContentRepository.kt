package com.github.beetv.data.repository.content

import com.github.beetv.data.model.GroupedItems
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.Page
import com.github.beetv.data.repository.ContentRepository

class LocalhostContentRepository : ContentRepository {
    override suspend fun groupContent(pageNum: Long, pageSize: Long): Page<GroupedItems> {
        TODO("Not yet implemented")
    }

    override suspend fun listContent(pageNum: Long, pageSize: Long): Page<Item> {
        TODO("Not yet implemented")
    }
}