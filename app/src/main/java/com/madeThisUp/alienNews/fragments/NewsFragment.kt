package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeThisUp.alienNews.adapters.NewsListAdapter
import com.madeThisUp.alienNews.databinding.FragmentNewsBinding
import com.madeThisUp.alienNews.newsApi.ConnectionStatus
import com.madeThisUp.alienNews.newsApi.ConnectionStatusManager
import com.madeThisUp.alienNews.repository.TokenCache
import com.madeThisUp.alienNews.repository.TokenNewsRepositoryImpl
import com.madeThisUp.alienNews.utility.showLongToastText
import com.madeThisUp.alienNews.viewModels.NewsViewModel
import kotlinx.coroutines.launch


fun ConnectionStatusManager.hasConnection(): Boolean =
    (TokenCache.tokenSet() && connectionStatus.value == ConnectionStatus.CONNECTED)


/**
 * Shows all the news of one channel
 */
class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding"
        }

    private val args: NewsFragmentArgs by navArgs()

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModel.Companion.NewsViewModelFactory(TokenNewsRepositoryImpl(), args.newsChannel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        binding.NewsRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                newsViewModel.news.collect { news ->
                    binding.NewsRecyclerView.adapter = NewsListAdapter(news) {
                        if(ConnectionStatusManager.hasConnection()) {
                            findNavController().navigate(
                                NewsFragmentDirections.actionNewsFragmentToNewsImagesFragment(it.imageIds!!.toTypedArray())
                            )
                        } else {
                            requireContext().showLongToastText("Connection issue. Check connection status")
                        }
                    }
                }
            }
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}