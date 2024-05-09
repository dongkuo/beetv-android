package com.github.beetv.data.model

data class Page<T>(val total: Long, val data: List<T>)

fun <T> emptyPage(): Page<T> = Page(0, emptyList())