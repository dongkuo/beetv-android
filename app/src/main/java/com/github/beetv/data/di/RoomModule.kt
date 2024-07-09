package com.github.beetv.data.di

import android.content.Context
import androidx.room.Room
import com.github.beetv.data.dao.BeetvDatabase
import com.github.beetv.data.dao.ChannelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideBeetvDatabase(
        @ApplicationContext context: Context,
    ): BeetvDatabase =
        Room.databaseBuilder(context, BeetvDatabase::class.java, BeetvDatabase.DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideSearchWeatherDao(
        beetvDatabase: BeetvDatabase
    ): ChannelDao = beetvDatabase.channelDao()

}