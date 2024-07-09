package com.github.beetv.data.repository.content.spider

import android.content.Context
import android.util.Log
import com.github.beetv.data.model.Category
import com.github.beetv.data.model.Channel
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.ItemDetail
import com.github.beetv.data.model.ItemGroup
import com.github.beetv.data.model.Page
import com.github.beetv.data.model.Source
import com.github.beetv.data.model.SourceGroup
import com.github.beetv.data.repository.content.ChannelContentRepository
import com.github.beetv.spider.Spider
import dagger.hilt.android.qualifiers.ApplicationContext
import dalvik.system.DexClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpiderRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : ChannelContentRepository {

    private val spiderMap: Map<Channel, Spider> = mapOf()


    override suspend fun groupContent(channel: Channel): List<ItemGroup> {
        return runSpider(channel) {
            val categoryWithSimpleMediaList = fetchCategoryWithMedia()
            categoryWithSimpleMediaList.map {
                val items = it.medias.map { media ->
                    Item(media.id, media.cover, media.name, media.desc)
                }
                ItemGroup(it.category.name, items)
            }
        }
    }

    override suspend fun listContent(pageNum: Long, pageSize: Long, channel: Channel): Page<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun detailItem(id: String, channel: Channel): ItemDetail {
        Log.i("SpiderRepository", "id=$id, channel=$channel")
        return runSpider(channel) {
            val media = fetchMediaById(id)
            if (media == null) {
                ItemDetail.EMPTY_ITEM_DETAIL
            } else {
                ItemDetail(
                    id = id,
                    name = media.name,
                    cover = media.cover,
                    actors = emptyList(),
                    summary = media.summary,
                    category = media.category?.run { Category(id, name) }
                        ?: Category.EMPTY_CATEGORY,
                    releaseYear = media.releaseYear ?: 0,
                    originCountry = media.originCountry ?: "",
                    sourceGroups = media.sourceGroups.map { sourceGroup ->
                        SourceGroup(
                            sourceGroup.name,
                            sourceGroup.sources.map { Source(it.name, it.url) })
                    }
                )
            }
        }
    }

    override suspend fun resolveUrl(url: String, channel: Channel): String {
        return runSpider(channel) {
            resolveMediaUrl(url)
        }
    }


    private suspend fun <T> runSpider(
        channel: Channel,
        block: suspend Spider.() -> T
    ): T {
        var spider = spiderMap[channel]
        if (spider == null) {
            val spiderChannelConfig = channel.config as SpiderChannelConfig
            val file = File(context.filesDir, spiderChannelConfig.jarPath)
            file.setReadOnly()
            val classLoader = DexClassLoader(
                file.absolutePath, context.cacheDir.absolutePath, null, this.javaClass.classLoader
            )
            val spiderClass = classLoader.loadClass(spiderChannelConfig.className)
            spider = spiderClass.constructors[0].newInstance() as Spider
        }
        return withContext(Dispatchers.IO) {
            block(spider)
        }
    }
}
