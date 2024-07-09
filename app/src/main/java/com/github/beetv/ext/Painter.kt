package com.github.beetv.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

const val SCHEMA_RESOURCE = "res://"

fun resource(id: Int): String {
    return "$SCHEMA_RESOURCE$id"
}

@Composable
fun painter(icon: String): Painter {
    if (icon.startsWith(SCHEMA_RESOURCE)) {
        return painterResource(id = icon.substring(SCHEMA_RESOURCE.length).toInt())
    }
    TODO()
}