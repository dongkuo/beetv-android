package com.github.beetv.data.util

import androidx.room.TypeConverter
import com.github.beetv.data.model.ViewStyle

class Converters {

    @TypeConverter
    fun fromViewStyleJson(json: String): ViewStyle = json.fromJson(ViewStyle::class)

    @TypeConverter
    fun toViewStyleJson(viewStyle: ViewStyle): String = viewStyle.toJson()
}