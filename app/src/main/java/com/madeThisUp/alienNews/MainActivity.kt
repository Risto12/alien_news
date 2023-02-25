package com.madeThisUp.alienNews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.madeThisUp.alienNews.databinding.ActivityFullscreenBinding
import com.madeThisUp.alienNews.fragments.ConnectFragment
import com.madeThisUp.alienNews.fragments.NewsChannelFragmentDirections
import com.madeThisUp.alienNews.newsApi.ConnectionStatus
import com.madeThisUp.alienNews.newsApi.ConnectionStatusManager
import com.madeThisUp.alienNews.utility.showLongToastText

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.connection_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.connection_settings) return true // The top menu item that is used as the icon "holder" was clicked

        when(item.itemId) {
            R.id.connection_disconnect -> run {
                ConnectionStatusManager.setDisconnected()
                return true
            }
            R.id.connection_connect -> run {
                ConnectFragment().show(supportFragmentManager, "ConnectFragment")
                return true
            }
            else -> run {
                showLongToastText("Unknown item clicked")
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val iconId = when (ConnectionStatusManager.connectionStatus.value) {
            ConnectionStatus.CONNECTED -> R.drawable.ic_connection
            ConnectionStatus.ERROR -> R.drawable.ic_no_connection
            ConnectionStatus.DISCONNECT -> R.drawable.ic_no_settings
        }
        menu.getItem(0).icon = AppCompatResources.getDrawable(this, iconId)
        return true
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