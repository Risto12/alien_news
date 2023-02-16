package com.madeThisUp.alienNews.holders

import androidx.recyclerview.widget.RecyclerView
import com.madeThisUp.alienNews.databinding.NewsTextContentHolderBinding
import com.madeThisUp.alienNews.models.Content

class NewsHolder(private val binding: NewsTextContentHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun updateNewsInformation(newsContent: Content) {
        binding.apply {
            newsHeader.text = newsContent.header
            newsDate.text = newsContent.date
            newsText.text = newsContent.text
        }
    }
}
