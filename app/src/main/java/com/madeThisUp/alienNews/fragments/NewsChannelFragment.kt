package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.adapters.NewsChannelListAdapter
import com.madeThisUp.alienNews.databinding.FragmentNewsChannelBinding
import com.madeThisUp.alienNews.models.NewsChannelsViewModel
import com.madeThisUp.alienNews.newsApi.NewsMockApi
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date


fun Date.toYearMonthDayFormat(): String = DateFormat.getDateInstance().format(this)


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