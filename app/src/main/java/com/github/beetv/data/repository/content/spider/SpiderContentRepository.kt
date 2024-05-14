package com.github.beetv.data.repository.content.spider

import android.util.Log
import com.github.beetv.BeetvApplication
import com.github.beetv.data.model.GroupedItems
import com.github.beetv.data.model.Item
import com.github.beetv.data.model.Page
import com.github.beetv.data.repository.ContentRepository
import com.github.beetv.spider.Spider
import dalvik.system.DexClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class SpiderContentRepository(spiderConfig: SpiderConfig) : ContentRepository {

    private val spider: Spider

    init {
        Log.i("SpiderContentRepository", "filesDir: ${BeetvApplication.filesDirPath}")
        val file = File(BeetvApplication.filesDirPath, spiderConfig.jarPath)
        file.setReadOnly()
        val classLoader = DexClassLoader(
            file.absolutePath, null, null, this.javaClass.classLoader
        )
        val spiderClass = classLoader.loadClass(spiderConfig.className)
        spider = spiderClass.constructors[0].newInstance() as Spider
    }

    override suspend fun groupContent(): List<GroupedItems> {
        return withContext(Dispatchers.IO) {
            val categoryWithSimpleMediaList = spider.fetchCategoryWithMedia()
            categoryWithSimpleMediaList.map {
                val items = it.medias.map { media ->
                    Item(media.id, media.cover, media.name, media.desc)
                }
                GroupedItems(it.category.name, items)
            }
        }
    }

    override suspend fun listContent(pageNum: Long, pageSize: Long): Page<Item> {
        TODO("Not yet implemented")
    }
}