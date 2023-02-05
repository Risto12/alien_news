package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madeThisUp.alienNews.databinding.FragmentNewsChannelBinding
import com.madeThisUp.alienNews.databinding.NewsChannelHolderBinding
import java.text.DateFormat
import java.time.Instant
import java.util.Date


fun Date.toYearMonthDayFormat(): String = DateFormat.getDateInstance().format(this)

data class NewsChannel(
    val name: String,
    val lastUpdate: Date,
    val brakingNews: Boolean
)

class NewsChannelHolder(
    private val binding: NewsChannelHolderBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun updateChannelInformation(channel: NewsChannel) {
        binding.apply {
            newsChannelName.text = channel.name
            newsChannelLatestUpdate.text = channel.lastUpdate.toYearMonthDayFormat()
            newChannelBrakingNews.visibility = if (channel.brakingNews) View.VISIBLE else View.GONE
        }
    }

    fun setOnClickListener(onClickChannel: (channelName: String) -> Unit) {
        binding.root.setOnClickListener { onClickChannel(binding.newsChannelName.text.toString()) } // TODO investigate why the click sounds two times but not repeated clicks is logged
    }
}

class NewsChannelListAdapter(
    private val newsChannels: List<NewsChannel>,
    private val onClickChannel: (channelName: String) -> Unit,
) : RecyclerView.Adapter<NewsChannelHolder>() {
    // MEMO adapter responsibilities -> Creating necessary viewHolders and binding data to them
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsChannelHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsChannelHolderBinding.inflate(inflater, parent, false)
        return NewsChannelHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsChannelHolder, position: Int) {
        holder.apply {
            updateChannelInformation(newsChannels[position])
            setOnClickListener(onClickChannel)
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
        // TODO replace from database
        val mockChannels = listOf(
            NewsChannel(
                "test1",
                Date(),
                true
            ),
            NewsChannel(
                "test2",
                Date.from(Instant.now().minusMillis(5000000000L)),
                false
            ),
            NewsChannel(
                "test3",
                Date(),
                false
            ),
            NewsChannel(
                "test4",
                Date(),
                false
            )
        )
        binding.NewsChannelRecyclerView.adapter = NewsChannelListAdapter(mockChannels
        ) { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() } // TODO NOT IMPLEMENTED
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