package com.madeThisUp.alienNews.newsApi

import android.util.Log
import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val NETWORK_ERROR_TAG = "News request issue"

// TODO there should be check to identify if this is token expired issue

class NewsRepositoryImpl: NewsRepository {
    private val newsApi: NewsApi

    init {
        newsApi = Retrofit
            .Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    override suspend fun fetchNewsChannels(): List<NewsChannel> {
        return try {
            newsApi.fetchChannels()
        } catch(e: Exception) {
            Log.e(
                NETWORK_ERROR_TAG,
                "Fetch news channels request failed. Returning empty list",
                e
            )
            listOf()
        }
    }

    override suspend fun fetchChannelNews(channel: String): List<News> {
        return try {
            newsApi.fetchChannel(name = channel)
        } catch(e: Exception) {
            Log.e(
                NETWORK_ERROR_TAG,
                "Fetch news request failed for channel:$channel. Returning empty list",
                e
            )
            listOf()
        }
    }

}