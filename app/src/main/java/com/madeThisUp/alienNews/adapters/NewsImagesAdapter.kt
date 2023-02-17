package com.madeThisUp.alienNews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madeThisUp.alienNews.databinding.NewsImageHolderBinding
import com.madeThisUp.alienNews.holders.NewsImageHolder
import com.madeThisUp.alienNews.newsApi.imageIdToImageUrl

class NewsImagesAdapter (
    private val imageUrls: List<String>
) : RecyclerView.Adapter<NewsImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsImageHolderBinding.inflate(inflater, parent, false)
        return NewsImageHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsImageHolder, position: Int) {
        holder.apply {
            updateImage(imageUrls[position].imageIdToImageUrl())
        }
    }

    override fun getItemCount(): Int = imageUrls.size
}