package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeThisUp.alienNews.adapters.NewsChannelListAdapter
import com.madeThisUp.alienNews.databinding.FragmentNewsChannelBinding
import com.madeThisUp.alienNews.newsApi.NETWORK_ERROR_TAG
import com.madeThisUp.alienNews.newsApi.NETWORK_TAG
import com.madeThisUp.alienNews.repository.NewsRepositoryAuthenticationException
import com.madeThisUp.alienNews.viewModels.NewsChannelsViewModel
import com.madeThisUp.alienNews.repository.TokenNewsRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val CHECK_NEWS_INTERVAL_IN_MILLI_SECONDS = 15000L

/**
 * Shows all the news channels available
 */
class NewsChannelFragment : Fragment() {
    private val newsChannelViewModel: NewsChannelsViewModel by viewModels()
    private val newsRepository: TokenNewsRepositoryImpl = TokenNewsRepositoryImpl()

    private var _binding: FragmentNewsChannelBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
                    ) { newsChannel ->
                        findNavController().navigate(
                        NewsChannelFragmentDirections
                            .actionNewsChannelFragmentToNewsFragment(newsChannel)
                    ) }
                }
            }
        }
        // TODO CHECK that this stops when different view is inflated
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                while (true) {
                    try {
                        newsRepository.fetchNewsChannels().let { newChannels ->
                            newsChannelViewModel.updateChannels(newChannels)
                        }
                    } catch (e: Exception) {
                        newsChannelViewModel.updateChannels(listOf())
                        when(e) {
                            is NewsRepositoryAuthenticationException -> Log.e(NETWORK_ERROR_TAG,"Exception during fetching channels", e)
                            else -> Log.e(NETWORK_ERROR_TAG,"Exception during fetching channels", e)
                        }
                        Log.e(NETWORK_ERROR_TAG,"Exception during fetching channels", e)
                    }
                    delay(CHECK_NEWS_INTERVAL_IN_MILLI_SECONDS)
                }
        }}
    }
}

