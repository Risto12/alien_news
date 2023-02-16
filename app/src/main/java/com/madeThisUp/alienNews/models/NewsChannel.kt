package com.madeThisUp.alienNews.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsChannel(
    val name: String,
    val header: String,
    val latestUpdate: String,
    val brakingNews: Boolean
)
