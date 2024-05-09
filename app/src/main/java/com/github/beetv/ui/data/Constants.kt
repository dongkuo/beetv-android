package com.github.beetv.ui.data

import com.github.beetv.R
import com.github.beetv.data.model.Item
import com.github.beetv.ui.components.Position
import com.github.beetv.ui.components.SideBarItem

val SIDE_BAR_ITEMS = listOf(
    SideBarItem("search", R.drawable.ic_search, "搜索", Position.TOP),
    SideBarItem("channel", R.drawable.ic_hive, "频道", Position.CENTER),
    SideBarItem("history", R.drawable.ic_watch_later, "历史", Position.CENTER),
    SideBarItem("collection", R.drawable.ic_star, "收藏", Position.Bottom),
    SideBarItem("adding", R.drawable.ic_add_box, "添加", Position.Bottom),
    SideBarItem("setting", R.drawable.ic_settings, "设置", Position.Bottom),
)

