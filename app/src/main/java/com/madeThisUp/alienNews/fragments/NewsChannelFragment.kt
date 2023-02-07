package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madeThisUp.alienNews.adapters.CONNECTION_ISSUE
import com.madeThisUp.alienNews.adapters.NewsChannelListAdapter
import com.madeThisUp.alienNews.databinding.FragmentNewsChannelBinding
import com.madeThisUp.alienNews.databinding.NewsChannelHolderBinding
import com.madeThisUp.alienNews.holders.NewsChannelHolder
import com.madeThisUp.alienNews.models.NewsChannel
import com.madeThisUp.alienNews.models.NewsChannelInfo
import com.madeThisUp.alienNews.newsApi.NewsApi
import com.madeThisUp.alienNews.newsApi.NewsMockApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.time.Instant
import java.util.Date


fun Date.toYearMonthDayFormat(): String = DateFormat.getDateInstance().format(this)

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

class NewsChannelFragment : Fragment() {
    private val newsChannelViewModel: NewsChannelsViewModel by viewModels {
        NewsChannelsViewModel.Companion.NewsChannelsViewModelFactory(NewsMockApi())
    }

    private var _binding: FragmentNewsChannelBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // With out this the view couldn't release view and would leak memory
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsChannelBinding.inflate(layoutInflater, container, false)
        binding.NewsChannelRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                newsChannelViewModel.newsChannel.collect { newsChannels ->
                    binding.NewsChannelRecyclerView.adapter = NewsChannelListAdapter(newsChannels
                    ) { Toast.makeText(requireContext(), "test", Toast.LENGTH_LONG).show() }
                }
            }
        }
    }
}