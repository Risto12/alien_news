package com.madeThisUp.alienNews

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.LinearLayout
import com.madeThisUp.alienNews.databinding.ActivityFullscreenBinding
import com.madeThisUp.alienNews.fragments.NewsChannelFragment


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.connection_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) { // otherwise duplicate fragments would be created
            // Code below was deprecated because nav controller handles creating the fragment
            // because its set up as start destination. If this code would have been here
            // it the previous fragment could have been seen under the new one
            supportFragmentManager
                .beginTransaction()
                .add( // TODO this
                    R.id.fullScreenFragmentContainer,
                    NewsChannelFragment(),
                    NewsChannelFragment::class.java.canonicalName as String // preventing rare case of name colliding
                ).commit()
        }
    }




}