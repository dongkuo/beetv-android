package com.github.beetv.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.fasterxml.jackson.core.type.TypeReference
import com.github.beetv.ext.fromJson
import com.github.beetv.ext.toJson
import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ChannelConfig
import com.github.beetv.data.model.ViewStyle

@Database(entities = [Channel::class], version = 1)
@TypeConverters(ChannelStyleJsonConverter::class, ChannelConfigJsonConverter::class)
abstract class BeetvDatabase : RoomDatabase() {

    abstract fun channelDao(): ChannelDao

    companion object {
        const val DATABASE_NAME = "beetv.db"
    }
}

class ChannelStyleJsonConverter :
    JsonConverter<ViewStyle>(object : TypeReference<ViewStyle>() {})

class ChannelConfigJsonConverter :
    JsonConverter<ChannelConfig>(object : TypeReference<ChannelConfig>() {})

abstract class JsonConverter<T>(private val typeReference: TypeReference<T>) {

    @TypeConverter
    fun fromJson(json: String): T = json.fromJson(typeReference)

    @TypeConverter
    fun toJson(bean: T): String? = bean?.toJson()
}