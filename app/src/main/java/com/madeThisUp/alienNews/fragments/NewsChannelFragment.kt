package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madeThisUp.alienNews.databinding.FragmentNewsChannelBinding
import com.madeThisUp.alienNews.databinding.NewsChannelHolderBinding
import java.util.Date


data class NewsChannel(
    val name: String,
    val lastUpdate: Date,
    val brakingNews: Boolean // TODO use alarm icon
)

class NewsChannelHolder(
    val binding: NewsChannelHolderBinding
) : RecyclerView.ViewHolder(binding.root)

class NewsChannelListAdapter(
    private val newsChannels: List<NewsChannel>
) : RecyclerView.Adapter<NewsChannelHolder>() {
    // MEMO adapter responsibilities -> Creating necessary viewHolders and binding data to them
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsChannelHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsChannelHolderBinding.inflate(inflater, parent, false)
        return NewsChannelHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsChannelHolder, position: Int) {
        val channel = newsChannels[position]
        holder.apply {
            binding.newsChannelHolderChannel.text = channel.name
            binding.newsChannelLatestUpdate.text = channel.lastUpdate.toString()
        }
    }

    override fun getItemCount(): Int = newsChannels.size
}

class NewsChannelFragment : Fragment() {
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
        binding.NewsChannelRecyclerView.adapter = NewsChannelListAdapter(listOf(
            NewsChannel(
                "test1",
                Date(),
                false
            ),
            NewsChannel(
                "test2",
                Date(),
                false
            )
        ))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewsChannelFragment.
         */
        @JvmStatic
        fun newInstance() = NewsChannelFragment()
    }
}