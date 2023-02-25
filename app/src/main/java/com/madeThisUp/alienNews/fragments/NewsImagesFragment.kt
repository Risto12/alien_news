package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.adapters.NewsImagesAdapter
import com.madeThisUp.alienNews.adapters.NewsListAdapter
import com.madeThisUp.alienNews.databinding.FragmentNewsBinding
import com.madeThisUp.alienNews.databinding.FragmentNewsImagesBinding
import com.madeThisUp.alienNews.holders.NewsImageHolder
import kotlinx.coroutines.launch

/**
 * Shows all the pictures in the chosen news
 */
class NewsImagesFragment : Fragment() {
    private var _binding: FragmentNewsImagesBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding"
        }

    private val args: NewsImagesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsImagesBinding.inflate(layoutInflater, container, false)
        binding.imagesRecyclerView.layoutManager = LinearLayoutManager(context) // TODO cache the images after first load
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imagesRecyclerView.adapter = NewsImagesAdapter(args.imageUrls.toList())
    }
}