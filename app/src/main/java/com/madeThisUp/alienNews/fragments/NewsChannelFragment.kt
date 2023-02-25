package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import android.view.*

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.adapters.NewsChannelListAdapter
import com.madeThisUp.alienNews.databinding.FragmentNewsChannelBinding
import com.madeThisUp.alienNews.viewModels.NewsChannelsViewModel
import com.madeThisUp.alienNews.newsApi.ConnectionStatusManager
import com.madeThisUp.alienNews.newsApi.ConnectionStatus
import com.madeThisUp.alienNews.repository.TokenNewsRepositoryImpl
import com.madeThisUp.alienNews.utility.showLongToastText
import kotlinx.coroutines.launch


/**
 * Shows all the news channels available
 */
class NewsChannelFragment : Fragment() {
    private val newsChannelViewModel: NewsChannelsViewModel by viewModels {
        NewsChannelsViewModel.Companion.NewsChannelsViewModelFactory(TokenNewsRepositoryImpl())
    }
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
    }
}