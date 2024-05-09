package com.github.beetv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.beetv.data.util.fromJson

@Entity
data class Channel(
    @PrimaryKey val id: Long,
    val icon: Int,
    val title: String,
    val type: ChannelType,
    val viewType: ViewType,
    val viewStyle: ViewStyle = defaultViewStyle,
    val config: String = ""
) {
    inline fun <reified T : Any> deserializeConfig(): T = config.fromJson(T::class)
}

val noneChannel = Channel(0, 0, "", ChannelType.NONE, ViewType.NONE, defaultViewStyle)
