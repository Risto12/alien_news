package com.madeThisUp.alienNews.models

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Token(val token: String)