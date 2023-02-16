package com.madeThisUp.alienNews.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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