package com.madeThisUp.alienNews.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeThisUp.alienNews.R
import com.madeThisUp.alienNews.databinding.FragmentNewsBinding
import com.madeThisUp.alienNews.databinding.FragmentNewsChannelBinding


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding"
        }


    override fun onDestroyView() {
        super.onDestroyView()
        // With out this the view couldn't release view and would leak memory
        Log.d("vieww", "testing")
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}