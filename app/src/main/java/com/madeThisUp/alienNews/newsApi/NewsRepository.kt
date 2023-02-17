package com.madeThisUp.alienNews.newsApi

import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel

interface NewsRepository {
    suspend fun fetchNewsChannels(): List<NewsChannel>
    suspend fun fetchChannelNews(channel: String): List<News>
}