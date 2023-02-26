package com.madeThisUp.alienNews.repository

import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel

class NewsRepositoryException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
class NewsRepositoryAuthenticationException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

class NewsRepositoryCredentialsNotSetException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
interface NewsRepository {
    suspend fun fetchNewsChannels(): List<NewsChannel>
    suspend fun fetchChannelNews(channel: String): List<News>
}