package com.madeThisUp.alienNews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.madeThisUp.alienNews.databinding.ActivityFullscreenBinding
import com.madeThisUp.alienNews.fragments.ConnectFragment
import com.madeThisUp.alienNews.newsApi.ApiConnection
import com.madeThisUp.alienNews.newsApi.ConnectionStatus


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

/* Just example if there wouldn't be navigation and would inflate the fragment here
if (savedInstanceState == null) { // otherwise duplicate fragments would be created

    // Code below was deprecated because nav controller handles creating the fragment
    // because its set up as start destination. If this would be here the newsChannel fragment
    // could be seen in the background even if new fragment is created on top
    supportFragmentManager
        .beginTransaction()
        .add( // TODO this
            R.id.fullScreenFragmentContainer,
            NewsChannelFragment(),
            NewsChannelFragment::class.java.canonicalName as String // preventing rare case of name colliding
        ).commit()
}*/