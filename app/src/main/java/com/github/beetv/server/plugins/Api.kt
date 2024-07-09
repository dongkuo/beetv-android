package com.github.beetv.server.plugins

import android.content.Context
import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.server.api.androidResourceApi
import com.github.beetv.server.api.channelApi
import com.github.beetv.server.api.fileApi
import io.ktor.server.application.Application
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configApi(ctx: Context, channelRepository: ChannelRepository) {
    routing {
        route("/api") {
            androidResourceApi(ctx)
            channelApi(channelRepository)
            fileApi(ctx.filesDir)
        }
    }
}