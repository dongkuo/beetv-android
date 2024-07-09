package com.github.beetv.server.api

import com.github.beetv.server.plugins.ApiResponse
import com.github.beetv.util.RandomUtils
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.application.call
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.io.File
import java.util.UUID

data class UploadFileResp(val path: String)

val legal_dirs = setOf("spider")

fun Route.fileApi(filesDir: File) {
    post("/files/{dir}") {
        val dir = call.parameters["dir"]
        if (dir !in legal_dirs) {
            call.respond(ApiResponse.fail("上传路径有误"))
            return@post
        }
        val multipartData = call.receiveMultipart()
        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    val originalFileName = part.originalFileName as String
                    val path = dir + File.separator + buildFileName(originalFileName)
                    val saveFile = File(filesDir, path)
                    if (saveFile.parentFile?.exists() != true) {
                        saveFile.parentFile?.mkdirs()
                    }
                    part.streamProvider().use { inputStream ->
                        saveFile.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    call.respond(ApiResponse.succeed(UploadFileResp(path)))
                    return@forEachPart
                }

                else -> {}
            }
        }
    }
}

fun buildFileName(originalFileName: String): String {
    return RandomUtils.randomString(6) + "_" + originalFileName
}

