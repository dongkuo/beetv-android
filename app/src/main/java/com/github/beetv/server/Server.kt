package com.github.beetv.server

import com.github.beetv.data.repository.ChannelRepository
import com.github.beetv.server.plugins.configApi
import com.github.beetv.server.plugins.configApiResponseWrapper
import com.github.beetv.server.plugins.configSerialization
import com.github.beetv.server.plugins.configStaticResources
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Server @Inject constructor(
    private val ctx: android.app.Application,
    private val channelRepository: ChannelRepository
) {

    private val server by lazy {
        embeddedServer(
            Netty,
            port = 3000,
            host = "0.0.0.0",
        ) {
            module()
        }
    }

    private fun Application.module() {
        configStaticResources()
        configApiResponseWrapper()
        configSerialization()
        configApi(ctx, channelRepository)
    }

    suspend fun start() {
        withContext(Dispatchers.IO) {
            server.start(true)
        }
    }

    suspend fun stop() {
        withContext(Dispatchers.IO) {
            server.stop(1_000, 2_000)
        }
    }
}
