package com.github.beetv.data.repository

import com.github.beetv.data.dao.ChannelDao
import com.github.beetv.data.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChannelRepository(private val dao: ChannelDao) {

    suspend fun listChannel(): List<Channel> {
        return withContext(Dispatchers.IO) {
            dao.selectAll()
        }
    }

    suspend fun saveAll(vararg channels: Channel) {
        withContext(Dispatchers.IO) {
            dao.insertAll(*channels)
        }
    }
}