package com.madeThisUp.alienNews.holders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.madeThisUp.alienNews.databinding.NewsChannelHolderBinding
import com.madeThisUp.alienNews.fragments.toYearMonthDayFormat
import com.madeThisUp.alienNews.models.NewsChannel

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