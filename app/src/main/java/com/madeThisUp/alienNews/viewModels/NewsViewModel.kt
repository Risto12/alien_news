package com.madeThisUp.alienNews.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.madeThisUp.alienNews.models.Content
import com.madeThisUp.alienNews.newsApi.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    newsRepository: NewsRepository,
    private val channel: String
) : ViewModel() {
    private val _news: MutableStateFlow<List<Content>> = MutableStateFlow(listOf())
    val news: StateFlow<List<Content>>
        get() = _news.asStateFlow()

    init {
        viewModelScope.launch {
            newsRepository.fetchChannelNews(channel).let { news ->
                    Log.d("Output", news.size.toString())
                    _news.update { news.map { it.content }
                }
            }
        }
    }

    companion object {
        class NewsViewModelFactory(
            private val newsRepository: NewsRepository,
            private val channel: String
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NewsViewModel(newsRepository = newsRepository, channel = channel) as T
            }
        }
    }
}