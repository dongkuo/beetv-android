package com.github.beetv.data.model

data class ItemDetail(
    val id: String = "",
    val name: String = "",
    val cover: String = "",
    val actors: List<String> = emptyList(),
    val summary: String = "",
    val category: Category = Category.EMPTY_CATEGORY,
    val releaseYear: Int = 0,
    val originCountry: String = "",
    val sourceGroups: List<SourceGroup> = emptyList()
) {
    companion object {
        val EMPTY_ITEM_DETAIL = ItemDetail()
    }
}

data class SourceGroup(val name: String, val sources: List<Source>)

data class Source(
    val name: String,
    val url: String
)


data class Category(
    val id: String,
    val name: String,
) {
    companion object {
        val EMPTY_CATEGORY = Category("", "-")
    }
}