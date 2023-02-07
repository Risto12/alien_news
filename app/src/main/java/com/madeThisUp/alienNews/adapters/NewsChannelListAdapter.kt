package com.madeThisUp.alienNews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.madeThisUp.alienNews.databinding.ChannelConnectionIssueHolderBinding
import com.madeThisUp.alienNews.databinding.NewsChannelHolderBinding
import com.madeThisUp.alienNews.holders.ConnectionIssueHolder
import com.madeThisUp.alienNews.holders.NewsChannelHolder
import com.madeThisUp.alienNews.models.NewsChannel

const val CONNECTION_ISSUE_VIEW = 2
const val CHANNEL_VIEW = 1

const val CONNECTION_ISSUE = 1

class NewsChannelListAdapter(
    private val newsChannels: List<NewsChannel>,
    private val onClickChannel: (channelName: String) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {
    // MEMO adapter responsibilities -> Creating necessary viewHolders and binding data to them

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == CHANNEL_VIEW) {
            val binding = NewsChannelHolderBinding.inflate(inflater, parent, false)
            NewsChannelHolder(binding)
        } else {
            val binding = ChannelConnectionIssueHolderBinding.inflate(inflater, parent, false)
            ConnectionIssueHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if(newsChannels.isEmpty()) CONNECTION_ISSUE_VIEW else CHANNEL_VIEW


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(newsChannels.isEmpty()) return
        (holder as NewsChannelHolder).apply {
            updateChannelInformation(newsChannels[position])
            setOnClickListener(onClickChannel)
        }
    }

    // TODO make sure this doesn't create side-effects
    // More elegant approach would be to use another view when this is loading
    // This is experimentation app and I wanted to try rendering different type of views
    // from same recycler view. That is the reason this is used vs loader view
    override fun getItemCount(): Int =
        if(newsChannels.isEmpty()) CONNECTION_ISSUE else newsChannels.size
}