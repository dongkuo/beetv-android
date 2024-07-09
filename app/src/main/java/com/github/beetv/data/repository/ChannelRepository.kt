package com.github.beetv.data.repository

import com.github.beetv.data.dao.ChannelDao
import com.github.beetv.data.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelRepository @Inject constructor(private val dao: ChannelDao) {

    suspend fun listChannel(): List<Channel> {
        return withContext(Dispatchers.IO) {
            dao.selectAll()
        }
    }

    suspend fun findById(id: Long): Channel {
        return withContext(Dispatchers.IO) {
            dao.selectById(id) ?: Channel.NONE
        }
    }

    suspend fun add(channel: Channel) {
        withContext(Dispatchers.IO) {
            dao.insert(channel)
        }
    }

    suspend fun addAll(vararg channels: Channel) {
        withContext(Dispatchers.IO) {
            dao.insertAll(*channels)
        }
    }

    suspend fun update(channel: Channel) {
        withContext(Dispatchers.IO) {
            dao.update(channel)
        }
    }

    suspend fun remove(id: Long) {
        withContext(Dispatchers.IO) {
            dao.delete(Channel.NONE.copy(id = id))
        }
    }
}