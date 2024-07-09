package com.github.beetv.data.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.github.beetv.data.repository.content.spider.SpiderChannelConfig

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type"
)
@JsonSubTypes(JsonSubTypes.Type(SpiderChannelConfig::class, name = "SPIDER"))
abstract class ChannelConfig(val type: ChannelType) {
    object NONE : ChannelConfig(ChannelType.NONE)
}

