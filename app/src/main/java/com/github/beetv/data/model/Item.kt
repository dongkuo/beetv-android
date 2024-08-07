package com.github.beetv.data.model

data class Item(
    val id: String,
    val thumbnail: String,
    val title: String,
    val desc: String,
)

data class ItemGroup(val title: String, val items: List<Item>)