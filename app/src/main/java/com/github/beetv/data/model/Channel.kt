package com.github.beetv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.github.beetv.data.repository.content.spider.SpiderChannelConfig
import com.github.beetv.ext.fromJson
import com.github.beetv.ext.toJson
import com.github.beetv.server.api.AddChannelReq
import com.github.beetv.ui.data.MockData

@Entity
data class Channel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val icon: String = "",
    val title: String = "",
    val type: ChannelType = ChannelType.NONE,
    val style: ViewStyle = ViewStyle.DEFAULT,
    val config: ChannelConfig = ChannelConfig.NONE
) {

    companion object {
        val NONE = Channel()
    }
}

fun main() {
//    val channelJson = MockData.mockChannels[0].toJson()
//    println(channelJson)
//
//    val channel = channelJson.fromJson(Channel::class)
//    println(channel)

    val myChannelJson = """
        {
            "icon": "res://2131165279",
            "title": "美剧TV",
            "type": "SPIDER",
            "style": {
                "type": "POSTER_FLOW",
                "thumbWidth": 160,
                "thumbAspectRatio": 0.75
            },
            "config": {
                "jarPath": "spider/cuxh9p_classes.dex",
                "className": "com.github.beetv.spider.XTSpider"
            }
        }
    """.trimIndent()
    val addChannelReq = myChannelJson.fromJson(AddChannelReq::class)
    println(addChannelReq)
    println(addChannelReq.toJson())
}