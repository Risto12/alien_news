package com.madeThisUp.alienNews.newsApi

import com.madeThisUp.alienNews.models.NewsChannel
import com.madeThisUp.alienNews.models.NewsChannelInfo
import kotlinx.coroutines.delay
import java.util.*

class NewsMockRepositoryImpl : NewsRepository {

    private data class ChannelAndNews(
        val newsChannel: NewsChannel,
        val newsChannelInfo: List<NewsChannelInfo>
    )

    private fun getChannels(): List<ChannelAndNews> {
        return listOf(
            ChannelAndNews(
                NewsChannel(
                    name = "channel1",
                    lastUpdate = Date(),
                    brakingNews = false
                ),
                listOf(
                    NewsChannelInfo(
                        news = "Some text",
                        updated = Date()
                    ),
                    NewsChannelInfo(
                        news = "Some text",
                        updated = Date()
                    )
                )
            ),
            ChannelAndNews(
                NewsChannel(
                    name = "channel2",
                    lastUpdate = Date(),
                    brakingNews = false
                ),
                listOf(
                    NewsChannelInfo(
                        news = "Some text",
                        updated = Date()
                    ),
                    NewsChannelInfo(
                        news = "Some text",
                        updated = Date()
                    )
                )
            ),
            ChannelAndNews(
                NewsChannel(
                    name = "channel3",
                    lastUpdate = Date(),
                    brakingNews = false
                ),
                listOf(
                    NewsChannelInfo(
                        news = "Some text",
                        updated = Date()
                    ),
                    NewsChannelInfo(
                        news = "Some text",
                        updated = Date()
                    )
                )
            ),
        )
    }

    override suspend fun fetchNewsChannels(): List<NewsChannel> {
        delay(2000)
        return getChannels().map { it.newsChannel }
    }

    override suspend fun fetchNewsChannel(channel: String): List<NewsChannelInfo> {
        delay(2000)
        return getChannels().first { it.newsChannel.name == channel }.newsChannelInfo
    }

    override suspend fun fetchChannelImage(imageId: String): Any? {
        TODO("Not yet implemented")
    }

}