package com.madeThisUp.alienNews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madeThisUp.alienNews.databinding.NewsTextContentHolderBinding
import com.madeThisUp.alienNews.holders.NewsTextContentHolder
import com.madeThisUp.alienNews.models.Content

class NewsListAdapter(
    private val content: List<Content>,
    private val onClickSeeAllImages: (content: Content) -> Unit
) : RecyclerView.Adapter<NewsTextContentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsTextContentHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsTextContentHolderBinding.inflate(inflater, parent, false)
        return NewsTextContentHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsTextContentHolder, position: Int) {
        holder.apply {
            updateNewsInformation(content[position])
            setOnSeeAllImagesClicked(content[position], onClickSeeAllImages)
        }
    }

    override fun getItemCount(): Int = content.size
}