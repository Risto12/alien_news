package com.madeThisUp.alienNews.repository

import com.madeThisUp.alienNews.models.Content
import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel
import kotlinx.coroutines.delay
import java.util.*

class NewsMockRepositoryImpl : NewsRepository {

    private data class ChannelAndNews(
        val newsChannel: NewsChannel,
        val ChannelNews: List<News>
    )

    private fun getChannels(): List<ChannelAndNews> {
        return listOf(
            ChannelAndNews(
                NewsChannel(
                    name = "channel1",
                    latestUpdate = Date().toString(),
                    brakingNews = false,
                    header = "Aliens attack"
                ),
                listOf(
                    News(
                        Content(
                            header = "text",
                            text = "Some text",
                            date = Date().toString(),
                            brakingNews = true
                        )
                    ),
                    News(
                        Content(
                            header = "text",
                            text = "Some text",
                            date = Date().toString(),
                            brakingNews = false
                        )
                    )
                )
            ),
            ChannelAndNews(
                NewsChannel(
                    name = "channel2",
                    latestUpdate = Date().toString(),
                    brakingNews = false,
                    header = "Aliens attack"
                ),
                listOf(
                    News(
                    Content(
                        header = "text",
                        text = "Some text",
                        date = Date().toString(),
                        brakingNews = true
                    )),
                    News(
                    Content(
                        header = "text",
                        text = "Some text",
                        date = Date().toString(),
                        brakingNews = true
                    )
                )
            )),
            ChannelAndNews(
                NewsChannel(
                    name = "channel3",
                    latestUpdate = Date().toString(),
                    brakingNews = false,
                    header = "Aliens attack"
                ),
                listOf(
                    News(
                    Content(
                        header = "text",
                        text = "Some text",
                        date = Date().toString(),
                        brakingNews = true
                    )),
                        News(
                    Content(
                        header = "text",
                        text = "Some text",
                        date = Date().toString(),
                        brakingNews = true
                    ))
                )
            )
        )
    }

    override suspend fun fetchNewsChannels(): List<NewsChannel> {
        delay(2000)
        return getChannels().map { it.newsChannel }
    }

    override suspend fun fetchChannelNews(channel: String): List<News> {
        delay(2000)
        return getChannels().first { it.newsChannel.name == channel }.ChannelNews
    }

}