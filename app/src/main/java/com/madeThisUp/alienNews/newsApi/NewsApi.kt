package com.madeThisUp.alienNews.newsApi

import com.madeThisUp.alienNews.models.NewsChannelInfo
import com.madeThisUp.alienNews.models.NewsChannel

interface NewsApi {
    suspend fun fetchNewsChannels(): List<NewsChannel>
    suspend fun fetchNewsChannel(channel: String): List<NewsChannelInfo>
}