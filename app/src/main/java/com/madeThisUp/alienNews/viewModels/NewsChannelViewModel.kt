package com.madeThisUp.alienNews.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.madeThisUp.alienNews.data.AlienNewsCredentialsPreferences
import com.madeThisUp.alienNews.models.NewsChannel
import com.madeThisUp.alienNews.newsApi.NETWORK_ERROR_TAG
import com.madeThisUp.alienNews.newsApi.NETWORK_TAG
import com.madeThisUp.alienNews.repository.NewsRepository
import com.madeThisUp.alienNews.repository.NewsRepositoryAuthenticationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsChannelsViewModel(
    newsRepository: NewsRepository
) : ViewModel() {
    private val _newsChannels: MutableStateFlow<List<NewsChannel>> = MutableStateFlow(listOf())
    val newsChannel: StateFlow<List<NewsChannel>>
        get() = _newsChannels.asStateFlow()

    init {
        viewModelScope.launch {
                while (true) {
                        try {
                            newsRepository.fetchNewsChannels().let { newChannels ->
                                _newsChannels.update { newChannels }
                            }
                        } catch(e: NewsRepositoryAuthenticationException) {
                            Log.d(NETWORK_TAG, e.message.toString())
                        } catch (e: Exception) {
                            Log.e(NETWORK_ERROR_TAG,"Exception during fetching channels", e)
                        }
                    delay(6000) // TODO put smaller number
                }
            }
        }

    companion object {
        class NewsChannelsViewModelFactory(
            private val newsRepository: NewsRepository
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NewsChannelsViewModel(newsRepository = newsRepository) as T
            }
        }
    }
}