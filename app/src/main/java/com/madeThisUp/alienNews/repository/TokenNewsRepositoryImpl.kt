package com.madeThisUp.alienNews.repository

import android.util.Log
import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel
import com.madeThisUp.alienNews.newsApi.NETWORK_TAG
import com.madeThisUp.alienNews.newsApi.NEWS_BASE_URL_TOKEN
import com.madeThisUp.alienNews.newsApi.TokenNewsApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


// TODO there should be check to identify if this is token expired issue

object TokenCache {
    @Volatile
    var TOKEN = ""
    set(token) {
        field = "Bearer $token"
    }

    fun isTokenSet(): Boolean = TOKEN != ""
}

class TokenNewsRepositoryImpl(
    //private val savedInstanceState: Bundle // TODO a bit of experimentation
): NewsRepository {
    private val newsApi: TokenNewsApi = Retrofit
        .Builder()
        .baseUrl(NEWS_BASE_URL_TOKEN)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TokenNewsApi::class.java)

    //private fun getToken(): String = savedInstanceState.getString(TOKEN_KEY, "")
    private fun getToken(): String = TokenCache.TOKEN

    @Throws(NewsRepositoryAuthenticationException::class, NewsRepositoryException::class)
    private suspend fun <T>withErrorHandling(
        request: suspend () -> T
    ): T {
        return try {
            request()
        } catch(e: HttpException) {
            when(e.code()) {
                401 -> throw NewsRepositoryAuthenticationException("Authentication not valid")
                else -> throw NewsRepositoryException()
            }
        } catch(e: NewsRepositoryAuthenticationException) {
            throw e
        } catch(e: Exception) {
                throw NewsRepositoryException()
            }
        }

    private suspend fun validateToken() {
        val credentials = CredentialsPreferencesRepository.get().credentialsFlow.first()
        if(!TokenCache.isTokenSet()) {
            if(credentials.username.isNullOrEmpty() || credentials.password.isNullOrEmpty()) {
                throw NewsRepositoryAuthenticationException("Token not found")
            } else {
                acquireToken(credentials.username, credentials.password)
            }
        }
    }

    override suspend fun fetchNewsChannels(): List<NewsChannel> {
        validateToken()
        return withErrorHandling {
            newsApi.fetchChannels(token = getToken())
        }
    }

    override suspend fun fetchChannelNews(channel: String): List<News> {
        validateToken()
        return withErrorHandling {
            newsApi.fetchChannel(token = getToken(), name = channel)
        }
    }

    suspend fun acquireToken(username: String, password: String) {
        withErrorHandling {
            val result = newsApi.fetchToken(
                username.toRequestBody(),
                password.toRequestBody()
            ).token
            TokenCache.TOKEN = result
            Log.d(NETWORK_TAG, "Fetched token:${TokenCache.TOKEN}")
        }
    }
}