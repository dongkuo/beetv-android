package com.github.beetv.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.beetv.data.model.Channel

@Dao
interface ChannelDao {

    @Query("SELECT * FROM channel")
    fun selectAll(): List<Channel>

    @Query("SELECT * FROM channel WHERE id=:id ")
    fun selectById(id: Long): Channel?

    @Insert
    fun insert(channel: Channel)


    @Insert
    fun insertAll(vararg channels: Channel)

    @Update
    fun update(channel: Channel)

    @Delete
    fun delete(channel: Channel)
}