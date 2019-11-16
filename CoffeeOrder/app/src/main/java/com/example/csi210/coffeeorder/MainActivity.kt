package com.example.csi210.coffeeorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity(),
    CartFragment.OnFragmentInteractionListener,
    CoffeeFragment.OnFragmentInteractionListener {

    override fun getCart(): ArrayList<Coffee> {
        return arrayList
    }

    override fun addCoffee(coffee: Coffee) {
        arrayList.add(coffee)
    }

    override fun updateCart() {
        coffeeViewModel.coffeeList.observe(this, Observer {
            coffeeMenu?.findItem(R.id.coffee_cart)?.setTitle("Cart(${it.sumBy { it.qty }})")
        })
    }

    val arrayList = ArrayList<Coffee>()
    lateinit var coffeeViewModel: CoffeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        coffeeViewModel = ViewModelProviders.of(this).get(CoffeeViewModel::class.java)
    }

    var coffeeMenu: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        coffeeMenu = menu
        updateCart()
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var navController = findNavController(R.id.nav_host_fragment)
        when (item?.itemId) {
            R.id.coffee_cart -> navController.navigate(R.id.action_global_cartFragment)
            R.id.coffee_home -> navController.navigate(R.id.action_global_coffeeFragment)
        }
        return true
    }
}
