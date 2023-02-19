package com.madeThisUp.alienNews.holders

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.databinding.NewsImageHolderBinding
import com.madeThisUp.alienNews.repository.NETWORK_ERROR_TAG


class NewsImageHolder(
    private val binding: NewsImageHolderBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun updateImage(imageUrl: String) {
        try {
            binding.newsImage.load(imageUrl) {
                placeholder(R.drawable.ic_baseline_satellite_alt_24)
            }
        } catch(e: Exception) {
            Log.d(NETWORK_ERROR_TAG, "Fetching imageUrl:$imageUrl failed", e)
            binding.newsImage.load(R.drawable.ic_no_connection)
        }
    }
}