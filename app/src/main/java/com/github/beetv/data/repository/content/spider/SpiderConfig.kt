package com.github.beetv.data.repository.content.spider

import com.github.beetv.spider.Spider

data class SpiderConfig(
    val spiderJar: String,
    val spiderClass: Class<out Spider>,
)