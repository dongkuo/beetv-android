package com.github.beetv.data.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(PosterGridViewStyle::class, name = "POSTER_FLOW"),
    JsonSubTypes.Type(FileExplorerViewStyle::class, name = "FILE_EXPLORER"),
)
abstract class ViewStyle(val type: Type) {

    companion object {
        val DEFAULT = PosterGridViewStyle(160F, 0.75F)
    }

    enum class Type {
        POSTER_FLOW,
        FILE_EXPLORER,
    }
}

data class PosterGridViewStyle(val thumbWidth: Float = 0F, val thumbAspectRatio: Float = 0F) :
    ViewStyle(Type.POSTER_FLOW) {
}

data class FileExplorerViewStyle(
    val arrangement: Arrangement = Arrangement.GRID
) : ViewStyle(Type.FILE_EXPLORER) {

    enum class Arrangement {
        LIST,
        GRID
    }
}

