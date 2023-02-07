package com.madeThisUp.alienNews.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.madeThisUp.alienNews.newsApi.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsChannelsViewModel(
    newsApi: NewsApi
) : ViewModel() {

    private val _newsChannels: MutableStateFlow<List<NewsChannel>> = MutableStateFlow(listOf())
    val newsChannel: StateFlow<List<NewsChannel>>
        get() = _newsChannels.asStateFlow()

    init {
        viewModelScope.launch { // TODO use debugging tool to see this coroutines lifecycle
            withContext(Dispatchers.IO) {
                while (true) {
                    newsApi.fetchNewsChannels().let { newChannels ->
                        _newsChannels.update { newChannels }
                    }
                    delay(5000)
                }
            }
        }
    }

    companion object {
        class NewsChannelsViewModelFactory(
            private val newsApi: NewsApi
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NewsChannelsViewModel(newsApi = newsApi) as T
            }
        }
    }
}