package com.madeThisUp.alienNews.models

import java.util.*

data class NewsChannel(
    val name: String,
    val lastUpdate: Date,
    val brakingNews: Boolean
)
