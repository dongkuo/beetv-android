package com.github.beetv.ui.data

import org.apache.commons.text.StringSubstitutor


object Routes {
    const val HOME_ROUTE = "/home"
    const val PLAYER_ROUTE = "/player?url={url}"
    const val DETAIL_ROUTE = "/detail?channelId={channelId}&itemId={itemId}"

    fun detailPage(channelId: Long, itemId: String): String = DETAIL_ROUTE.fill(
        mapOf(
            "channelId" to channelId,
            "itemId" to itemId,
        )
    )

    fun playerPage(url: String): String = PLAYER_ROUTE.fill(mapOf("url" to url))
}

private fun <V> String.fill(values: Map<String, V>) =
    StringSubstitutor.replace(this, values, "{", "}")