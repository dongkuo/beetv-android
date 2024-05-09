package com.github.beetv.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.beetv.data.model.Channel
import com.github.beetv.data.util.Converters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Channel::class], version = 1)
@TypeConverters(Converters::class)
abstract class BeetvDatabase : RoomDatabase() {

    abstract fun channelDao(): ChannelDao

    companion object {
        @Volatile
        private var INSTANCE: BeetvDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BeetvDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        BeetvDatabase::class.java,
                        "beetv.db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}