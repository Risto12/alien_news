package com.madeThisUp.alienNews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.madeThisUp.alienNews.databinding.ChannelConnectionIssueHolderBinding
import com.madeThisUp.alienNews.databinding.NewsTextContentHolderBinding
import com.madeThisUp.alienNews.holders.ConnectionIssueHolder
import com.madeThisUp.alienNews.holders.NewsHolder
import com.madeThisUp.alienNews.models.Content


const val NEWS_VIEW = 2
const val IMAGE_VIEW = 1

class NewsListAdapter(
    private val content: List<Content>,
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == NEWS_VIEW) {
            val binding = NewsTextContentHolderBinding.inflate(inflater, parent, false)
            NewsHolder(binding)
        } else {
            // TODO IMAGE VIEW HERE
            val binding = ChannelConnectionIssueHolderBinding.inflate(inflater, parent, false)
            ConnectionIssueHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int = NEWS_VIEW // TODO can be image also

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as NewsHolder).apply {
            updateNewsInformation(content[position])
        }
    }

    override fun getItemCount(): Int = content.size
}