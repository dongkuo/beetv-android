package com.github.beetv.ui.data

import com.github.beetv.R
import com.github.beetv.ui.components.NavRailItem
import com.github.beetv.ui.components.NavRailItem.Position

val SEARCH_NAV_RAIL_ITEM = NavRailItem(R.drawable.ic_search, "搜索", Position.TOP)
val CHANNEL_NAV_RAIL_ITEM = NavRailItem(R.drawable.ic_hive, "频道", Position.CENTER, Routes.HOME_ROUTE)
val HISTORY_NAV_RAIL_ITEM = NavRailItem(R.drawable.ic_watch_later, "历史", Position.CENTER)
val COLLECTION_NAV_RAIL_ITEM = NavRailItem(R.drawable.ic_star, "收藏", Position.BOTTOM)
val ADDING_NAV_RAIL_ITEM = NavRailItem(R.drawable.ic_add_box, "添加", Position.BOTTOM)
val SETTING_NAV_RAIL_ITEM = NavRailItem(R.drawable.ic_settings, "设置", Position.BOTTOM)

val NAV_RAIL_ITEMS = listOf(
    SEARCH_NAV_RAIL_ITEM,
    CHANNEL_NAV_RAIL_ITEM,
    HISTORY_NAV_RAIL_ITEM,
    COLLECTION_NAV_RAIL_ITEM,
    ADDING_NAV_RAIL_ITEM,
    SETTING_NAV_RAIL_ITEM,
)


