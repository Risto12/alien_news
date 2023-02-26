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

class NewsChannelsViewModel: ViewModel() {
    private val _newsChannels: MutableStateFlow<List<NewsChannel>> = MutableStateFlow(listOf())
    val newsChannel: StateFlow<List<NewsChannel>>
        get() = _newsChannels.asStateFlow()

    fun updateChannels(newsChannels: List<NewsChannel>) =
        _newsChannels.update { newsChannels }
}