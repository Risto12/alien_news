package com.madeThisUp.alienNews.newsApi

import com.squareup.moshi.JsonClass
import retrofit2.http.GET

const val NEWS_BASE_URL = "http://10.0.2.2:8080/" // issue with self signed certificates so falling back to http
const val CHANNELS = "alien/news/v1/channels"

@JsonClass(generateAdapter = true)
data class NewsApiChannel(
    val name: String,
    val latestUpdate: String,
    val brakingNews: Boolean
)

interface NewsApi {
    @GET(CHANNELS)
    suspend fun fetchChannels(): List<NewsApiChannel>
}