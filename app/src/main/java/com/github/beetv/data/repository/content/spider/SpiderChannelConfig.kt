package com.github.beetv.data.repository.content.spider

import com.github.beetv.data.model.ChannelConfig
import com.github.beetv.data.model.ChannelType

data class SpiderChannelConfig(
    val jarPath: String = "",
    val className: String = "",
) : ChannelConfig(ChannelType.SPIDER)