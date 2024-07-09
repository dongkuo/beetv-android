package com.github.beetv.server.plugins

import android.util.Log
import io.ktor.server.application.Application
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.CallFailed
import io.ktor.server.application.install
import io.ktor.server.response.respond

const val CODE_SUCCESS = 0
const val CODE_FAILURE = 1000

fun Application.configApiResponseWrapper() {
    install(ApiResponseWrapperPlugin)
}

private val ApiResponseWrapperPlugin = createApplicationPlugin("ApiResponseWrapperPlugin") {
    onCallRespond { _ ->
        transformBody { body ->
            if (body is ApiResponse || body is ByteArray) {
                body
            } else {
                ApiResponse.succeed(body)
            }
        }
    }

    on(CallFailed) { call, cause ->
        if (cause is ApiException) {
            call.respond(ApiResponse(cause.code, cause.message))
        } else {
            Log.e("ApiResponseWrapperPlugin", "系统出现异常", cause)
            call.respond(ApiResponse(CODE_FAILURE, data = "系统出现异常"))
        }
    }
}

data class ApiResponse(val code: Int, val message: String? = null, val data: Any? = null) {

    companion object {
        fun succeed(data: Any? = null, code: Int = CODE_SUCCESS): ApiResponse {
            return ApiResponse(CODE_SUCCESS, data = data)
        }

        fun fail(message: String, code: Int = CODE_FAILURE): ApiResponse {
            return ApiResponse(code, message, null)
        }
    }
}

class ApiException(
    override val message: String,
    override val cause: Throwable? = null,
    val code: Int = CODE_FAILURE
) : RuntimeException(message, cause)