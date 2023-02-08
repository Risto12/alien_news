package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
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
import com.madeThisUp.alienNews.models.NewsChannelsViewModel
import com.madeThisUp.alienNews.newsApi.ApiConnection
import com.madeThisUp.alienNews.newsApi.ConnectionStatus
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.connection_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.connection_settings) return true // The top menu item that is used as the icon "holder" was clicked

        when(item.itemId) {
            R.id.connection_disconnect -> run {
                ApiConnection.disconnect()
                invalidateOptionsMenu(activity)
            }
            R.id.connection_connect -> run {
                findNavController().navigate(
                    NewsChannelFragmentDirections
                        .actionNewsChannelFragmentToConnectFragment())
            }
            else ->
                Toast.makeText(requireContext(), "Unknown item clicked", Toast.LENGTH_LONG).show()
        }

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val iconId = when (ApiConnection.connectionStatus) {
            ConnectionStatus.CONNECTED -> R.drawable.ic_connection
            ConnectionStatus.ERROR -> R.drawable.ic_no_connection
            ConnectionStatus.DISCONNECT -> R.drawable.ic_no_settings
        }
        menu.getItem(0).icon = AppCompatResources.getDrawable(requireContext(), iconId)
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