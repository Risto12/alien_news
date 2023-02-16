package com.madeThisUp.alienNews.newsApi

import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

    override suspend fun fetchNewsChannels(): List<NewsChannel> = newsApi.fetchChannels()

    override suspend fun fetchChannelNews(channel: String): List<News> =
        newsApi.fetchChannel(name = channel)

    override suspend fun fetchChannelImage(imageId: String): Any? {
        TODO("Not yet implemented")
    }


}