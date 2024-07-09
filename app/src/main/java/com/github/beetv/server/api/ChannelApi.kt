package com.github.beetv.server.api

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ChannelConfig
import com.github.beetv.data.model.ChannelType
import com.github.beetv.data.model.ViewStyle
import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.data.repository.content.spider.SpiderChannelConfig
import com.github.beetv.server.plugins.ApiException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

data class AddChannelReq(
    val icon: String = "",
    val title: String = "",
    val type: ChannelType = ChannelType.NONE,
    val style: ViewStyle = ViewStyle.DEFAULT,
    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
    )
    @JsonSubTypes(JsonSubTypes.Type(SpiderChannelConfig::class, name = "SPIDER"))
    val config: ChannelConfig = ChannelConfig.NONE
)

fun Route.channelApi(channelRepository: ChannelRepository) {
    route("/channels") {
        get {
            val channels = channelRepository.listChannel()
            call.respond(channels)
        }

        post {
            val addChannelReq = call.receive<AddChannelReq>()
            val channel = Channel(
                icon = addChannelReq.icon,
                title = addChannelReq.title,
                type = addChannelReq.type,
                style = addChannelReq.style,
                config = addChannelReq.config
            )
            channelRepository.add(channel)
            call.respond(channel.id)
        }

        put {
            val channel = call.receive<Channel>()
            channelRepository.update(channel)
            call.respond(HttpStatusCode.OK)
        }

        delete {
            val id = call.receiveParameters()["id"]?.toLong()
                ?: throw ApiException("参数id不能为空")
            channelRepository.remove(id)
        }

        post("/jar/upload") {

        }
    }
}
