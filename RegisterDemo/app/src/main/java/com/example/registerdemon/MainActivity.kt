package com.example.registerdemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), InformationFragment.OnFragmentInteractionListener,
    ProfileFragment.OnFragmentInteractionListner {

    override fun getUserProfile(): String {
        return "Hello, $username \n Date of birth: $dob"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.nav_host_frag)
        toolbar.setupWithNavController(navController)

    }

    var username = ""
    var password = ""
    var dob = ""
    override fun saveInformation(u: String, p: String, d: String) {
        username = u
        password = p
        dob = d
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
        when (item?.itemId) {
            R.id.action_home -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_welcomeFragment)
        }
        return true
    }


}
