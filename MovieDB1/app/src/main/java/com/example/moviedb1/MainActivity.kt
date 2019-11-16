package com.example.moviedb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.nav_host_frag)
        toolbar.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.nowPlaying_item -> {

                var bundle = Bundle()
                bundle.putString("style", "Now Playing")
                findNavController(R.id.nav_host_frag).navigate(R.id.action_global_nowPlaying, bundle)

            }
            R.id.upComing_item -> {
                var bundle = Bundle()
                bundle.putString("style", "Up Coming")
                findNavController(R.id.nav_host_frag).navigate(R.id.action_global_nowPlaying, bundle)
            }
            R.id.saved_item -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_saved)
        }
        return super.onOptionsItemSelected(item)
    }
}
