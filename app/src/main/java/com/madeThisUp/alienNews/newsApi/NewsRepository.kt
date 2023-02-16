package com.madeThisUp.alienNews.newsApi

import com.madeThisUp.alienNews.models.NewsChannelInfo
import com.madeThisUp.alienNews.models.NewsChannel

interface NewsRepository {
    suspend fun fetchNewsChannels(): List<NewsChannel>
    suspend fun fetchNewsChannel(channel: String): List<NewsChannelInfo>
    suspend fun fetchChannelImage(imageId: String): Any?
}