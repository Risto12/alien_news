package com.madeThisUp.alienNews.models

import com.madeThisUp.alienNews.newsApi.imageIdToImageUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


fun Content.toContentImages(): List<ContentImage>? = this.imageIds?.map { ContentImage(it) }

@JsonClass(generateAdapter = true)
data class News(
    @Json(name = "news")
    val content: Content
)

@JsonClass(generateAdapter = true)
data class Content(
    val header: String,
    val text: String,
    val date: String,
    val imageIds: List<String>? = null,
    val brakingNews: Boolean,
)

class ContentImage(_imageId: String) {
    val imageUrl = _imageId
    get() = field.imageIdToImageUrl()
}