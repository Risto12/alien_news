package com.madeThisUp.alienNews.newsApi

import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel
import com.madeThisUp.alienNews.models.Token
import okhttp3.FormBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

const val NEWS_BASE_URL = "http://10.0.2.2:8080/alien/news/v1/" // issue with self signed certificates so falling back to http
const val CHANNELS = "channels"
const val CHANNEL = "channel/{name}"
const val NEWS_IMAGE = "image/%s"
const val TOKEN_LOGIN = "login"

const val NEWS_BASE_URL_TOKEN = "http://10.0.2.2:8080/alien/news/v1/authenticated/token/" // issue with self signed certificates so falling back to http

const val NETWORK_ERROR_TAG = "Network request issue"
const val NETWORK_TAG = "Network request"

fun String.imageIdToImageUrl() = NEWS_BASE_URL + NEWS_IMAGE.format(this)

interface NewsApi {
    @GET(CHANNELS)
    suspend fun fetchChannels(): List<NewsChannel>

    @GET(CHANNEL)
    suspend fun fetchChannel(@Path("name") name: String): List<News>
}

interface TokenNewsApi {
    @GET(CHANNELS)
    suspend fun fetchChannels(@Header("Authorization") token: String): List<NewsChannel>

    @GET(CHANNEL)
    suspend fun fetchChannel(@Header("Authorization") token: String, @Path("name") name: String): List<News>

    @POST(TOKEN_LOGIN)
    @Multipart
    suspend fun fetchToken(@Part("username") username: RequestBody, @Part("password") password: RequestBody): Token

}