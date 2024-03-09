package com.sisco.typicode.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.sisco.typicode.domain.model.Photo

data class PhotosResponse(
    @SerializedName("albumId")
    val albumId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String,
) {
    fun toDomain() = Photo(
        id = id,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
}
