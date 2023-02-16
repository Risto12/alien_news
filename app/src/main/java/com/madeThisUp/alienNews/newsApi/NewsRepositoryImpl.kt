package com.madeThisUp.alienNews.newsApi

import android.util.Log
import com.madeThisUp.alienNews.models.NewsChannel
import com.madeThisUp.alienNews.models.NewsChannelInfo
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsRepositoryImpl: NewsRepository {
    private val newsApi: NewsApi

    init {
        val retrofit: Retrofit =
            Retrofit
                .Builder()
                .baseUrl(NEWS_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        newsApi = retrofit.create(NewsApi::class.java)
    }

    override suspend fun fetchNewsChannels(): List<NewsChannel> {
        val result = newsApi.fetchChannels()
        Log.d("result", result.first().name)
        return listOf()
    }

    override suspend fun fetchNewsChannel(channel: String): List<NewsChannelInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelImage(imageId: String): Any? {
        TODO("Not yet implemented")
    }


}