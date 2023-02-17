package com.madeThisUp.alienNews.newsApi

import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel
import retrofit2.http.GET
import retrofit2.http.Path

const val NEWS_BASE_URL = "http://10.0.2.2:8080/" // issue with self signed certificates so falling back to http
const val CHANNELS = "alien/news/v1/channels"
const val CHANNEL = "alien/news/v1/channel/{name}"
const val NEWS_IMAGE = "alien/news/v1/image/%s"

fun String.imageIdToImageUrl() = NEWS_BASE_URL + NEWS_IMAGE.format(this)

interface NewsApi {
    @GET(CHANNELS)
    suspend fun fetchChannels(): List<NewsChannel>

    @GET(CHANNEL)
    suspend fun fetchChannel(@Path("name") name: String): List<News>
}