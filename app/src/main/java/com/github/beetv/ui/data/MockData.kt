package com.github.beetv.ui.data

import com.github.beetv.R
import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.ChannelType
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.ViewType
import com.github.beetv.data.repository.content.spider.SpiderConfig
import com.github.beetv.data.util.toJson
import kotlin.random.Random

val items = listOf(
    Item(
        "1",
        "https://i0.hdslb.com/bfs/bangumi/image/37d1db666b98ae33f500b10b0476e6d805f00647.jpg@466w_622h.webp",
        "未来漫游指南",
        "刘慈欣的未来之旅"
    ),
    Item(
        "2",
        "https://i0.hdslb.com/bfs/bangumi/image/daca3c79a0d9ef6a58f1799e87b038415f432f1a.jpg@466w_622h.webp",
        "神奇的老字号",
        "探秘30个老字号"
    ),
    Item(
        "3",
        "https://i0.hdslb.com/bfs/bangumi/image/399426bedcc7c0797f71e30367610ee367935b57.png@466w_622h.webp",
        "未至之境（英配版）",
        "探寻华夏万物生灵"
    ),
    Item(
        "4",
        "https://i0.hdslb.com/bfs/bangumi/a3c7f199e40ed605f3062f6f2271d34c46fb1bc9.jpg@466w_622h.webp",
        "人体极限大挑战",
        "天赋异禀的人类"
    ),
    Item(
        "5",
        "https://i0.hdslb.com/bfs/bangumi/abcb89c478d7f143a1827d01ed2406a691fc3c85.jpg@466w_622h.webp",
        "生命时速·紧急救护120",
        "生命抢救刻不容缓"
    ),
    Item(
        "6",
        "https://i0.hdslb.com/bfs/bangumi/image/81910f6dc47e499929d2e4c234531e1b46285cb0.png@466w_622h.webp",
        "何以中国",
        "百年中国考古"
    ),
    Item(
        "7",
        "https://tu.qtfy30.cc/uploads/allimg/240215/1707929256198.jpg",
        "未来漫游指南",
        "刘慈欣的未来之旅"
    ),
    Item(
        "8",
        "https://i0.hdslb.com/bfs/bangumi/image/b7da741b7c942482ead15f05d6a0c20b53de1a0c.png@466w_622h.webp",
        "极岛森林",
        "一个月的生态之旅"
    )
)

fun randomItems() = items.shuffled().take(Random.nextInt(4, items.size))

val mockChannels = listOf(
    Channel(
        2,
        R.drawable.ic_movie,
        "美剧TV",
        ChannelType.SPIDER,
        ViewType.POSTER_FLOW,
        config = SpiderConfig("classes.dex", "com.github.beetv.spider.MeiJuTVSpider").toJson()
    ),
    Channel(
        3,
        R.drawable.ic_alipan,
        "阿里云盘1",
        ChannelType.ALI_PAN,
        ViewType.FILE_EXPLORER
    ),
    Channel(
        4,
        R.drawable.ic_alipan,
        "阿里云盘2",
        ChannelType.ALI_PAN,
        ViewType.FILE_EXPLORER
    ),
    Channel(
        5,
        R.drawable.ic_sd_storage,
        "本机",
        ChannelType.LOCALHOST,
        ViewType.FILE_EXPLORER
    ),
    Channel(
        6,
        R.drawable.ic_alist,
        "小雅AList",
        ChannelType.A_LIST,
        ViewType.FILE_EXPLORER
    ),
)


