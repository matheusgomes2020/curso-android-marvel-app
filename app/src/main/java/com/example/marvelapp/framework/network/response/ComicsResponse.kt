package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Character
import com.example.core.domain.model.Comic
import com.google.gson.annotations.SerializedName

data class ComicsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun ComicsResponse.toComicModel(): Comic {
    return Comic(
        id = this.id,
        this.thumbnail.getHttpsUrl()
    )
}
