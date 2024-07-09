package com.github.beetv.server.api

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.github.beetv.R
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondOutputStream
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

data class ResourceItem(val id: Int, val name: String)

val drawables = setOf(
    ResourceItem(R.drawable.ic_movie, "ic_movie"),
    ResourceItem(R.drawable.ic_alist, "ic_alist")
)

fun Route.androidResourceApi(ctx: Context) {
    route("/resources") {
        route("/drawables") {
            get {
                call.respond(drawables)
            }
            get("/{id}") {
                val id = call.parameters["id"]?.toInt()
                if (id == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                val bm = ctx.resources.getDrawable(id, ctx.theme).toBitmap()
                call.respondOutputStream(ContentType.Image.PNG) {
                    bm.compress(Bitmap.CompressFormat.PNG, 100, this)
                }
            }
        }
    }
}

