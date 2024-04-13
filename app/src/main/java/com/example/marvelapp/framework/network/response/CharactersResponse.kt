package com.example.marvelapp.framework.network.response

import com.example.core.domain.model.Character
import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse
)

fun CharactersResponse.toCharacterModel(): Character {
    return Character(
        id = this.id,
        name = this.name,
        this.thumbnail.getHttpsUrl()
    )
}
