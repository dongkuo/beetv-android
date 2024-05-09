package com.github.beetv.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.beetv.data.model.Channel

@Dao
interface ChannelDao {

    @Query("SELECT * FROM channel")
    fun selectAll(): List<Channel>

    @Insert
    fun insertAll(vararg channels: Channel)
}