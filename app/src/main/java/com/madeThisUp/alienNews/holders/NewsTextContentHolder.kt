package com.madeThisUp.alienNews.holders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.databinding.NewsTextContentHolderBinding
import com.madeThisUp.alienNews.models.Content
import com.madeThisUp.alienNews.newsApi.NETWORK_ERROR_TAG
import com.madeThisUp.alienNews.newsApi.imageIdToImageUrl

class NewsTextContentHolder(private val binding: NewsTextContentHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun updateNewsInformation(newsContent: Content) {
        binding.apply {
            newsHeader.text = newsContent.header
            newsDate.text = newsContent.date
            newsText.text = newsContent.text
            if(newsContent.imageIds.isNullOrEmpty()) {
                newsMainImage.visibility = View.GONE
                newsAllImages.visibility = View.GONE
            } else {
                val imageUrl = newsContent.imageIds.first().imageIdToImageUrl()
                try {
                    newsMainImage.load(imageUrl) {
                        placeholder(R.drawable.ic_baseline_satellite_alt_24)
                    }
                } catch(e: Exception) {
                    Log.e(NETWORK_ERROR_TAG, "Fetching imageUrl:$imageUrl failed", e)
                    newsMainImage.load(R.drawable.ic_no_connection)
                }
                if(newsContent.imageIds.size <= 1) newsAllImages.visibility = View.GONE
            }
        }
    }

    fun setOnSeeAllImagesClicked(content: Content, onClick: (content: Content) -> Unit) {
        binding.newsAllImages.setOnClickListener { onClick(content) }
    }
}
