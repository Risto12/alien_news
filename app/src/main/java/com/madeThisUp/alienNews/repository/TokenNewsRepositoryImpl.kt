package com.madeThisUp.alienNews.repository

import android.util.Log
import com.madeThisUp.alienNews.models.News
import com.madeThisUp.alienNews.models.NewsChannel
import com.madeThisUp.alienNews.newsApi.*
import kotlinx.coroutines.flow.first
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TokenCache {
    @Volatile
    var TOKEN = ""
    set(token) {
        field = "Bearer $token"
    }

    fun resetToken() {
        TOKEN = ""
    }

    fun tokenSet(): Boolean = TOKEN != ""
    fun noTokenSet(): Boolean = TOKEN == ""
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
            val exception = when(e.code()) {
                401 -> run {
                    TokenCache.resetToken()
                    NewsRepositoryAuthenticationException("Authentication not valid")
                }
                else -> NewsRepositoryException()
            }
            ConnectionStatusManager.setError()
            throw exception
        } catch(e: NewsRepositoryAuthenticationException) {
            ConnectionStatusManager.setError()
            throw e
        } catch(e: Exception) {
                ConnectionStatusManager.setError()
                throw NewsRepositoryException()
            }
        }

    /**
     * Checks that token has been acquired if username and password has been set
     * If no username and password throws NewsRepositoryCredentialsNotSetException
     * and sets status disconnected
     */
    private suspend fun acquireTokenIfNotSet() {
        if(TokenCache.noTokenSet()) { // TODO fix something went wrong ...
            val credentials = CredentialsPreferencesRepository.get().credentialsFlow.first()
            if(credentials.username.isNullOrEmpty() || credentials.password.isNullOrEmpty()) {
                throw NewsRepositoryCredentialsNotSetException()
            } else {
                acquireToken(credentials.username, credentials.password)
            }
        }
    }

    override suspend fun fetchNewsChannels(): List<NewsChannel> { acquireTokenIfNotSet()
        return withErrorHandling {
            newsApi.fetchChannels(token = getToken())
        }
    }

    override suspend fun fetchChannelNews(channel: String): List<News> {
        acquireTokenIfNotSet()
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
            ConnectionStatusManager.setStatusConnected()
            Log.d(NETWORK_TAG, "Fetched token:${TokenCache.TOKEN}")
        }
    }
}