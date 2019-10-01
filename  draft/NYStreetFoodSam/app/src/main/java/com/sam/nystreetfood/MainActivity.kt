package com.sam.nystreetfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.opencsv.CSVReaderHeaderAware
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ListScreen.OnFragmentInteractionListener,
    detailScreen.OnFragmentInteractionListener,
    savedRestaurantScreen.OnFragmentInteractionListener {

    var restaurantList = ArrayList<restaurantEntry>()
    var uid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseCSV()

        setSupportActionBar(findViewById(R.id.toolBar))
        val navController = findNavController(R.id.nav_host_frag)
        toolBar.setupWithNavController(navController)
        toolBar.overflowIcon =
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_menu_black_24dp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.actionFavorite -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_savedRestaurantScreen)
            R.id.actionHome -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_homeScreen)
            R.id.actionStyle -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_styleCategoryScreen)
            R.id.actionFood -> findNavController(R.id.nav_host_frag).navigate(R.id.action_global_foodCategoryScreen)
        }
        return true
    }


    override fun getRestaurantObject(): ArrayList<restaurantEntry> {
        return restaurantList
    }

    override fun updateRestaurantObject(uid: Int, rating: Int) {
        restaurantList[uid].rating = rating
    }

    fun parseCSV() {
        val reader =
            CSVReaderHeaderAware(resources.openRawResource(R.raw.times_square_food_beverage_locations).bufferedReader())
        val resultList = mutableListOf<Map<String, String>>()
        var line = reader.readMap()
        while (line != null) {
            resultList.add(line)
            line = reader.readMap()
        }

        resultList.forEach {
            restaurantList.add(
                restaurantEntry(
                    it.get("Company Name").toString(),
                    it.get("Subindustry").toString(),
                    it.get("Sub Subindustry").toString(),
                    it.get("Address").toString(),
                    it.get("Phone").toString(),
                    it.get("Website").toString(),
                    it.get("Postcode").toString(),
                    when (it.get("Subindustry").toString()) {
                        "Bar / Lounge" -> R.drawable.bar
                        "Cafe / Deli" -> R.drawable.cafe
                        "Catering" -> R.drawable.catering
                        "Coffee" -> R.drawable.coffee
                        "Consumables" -> R.drawable.consumables
                        "Full Serve" -> R.drawable.fullservice
                        "Quick Serve" -> R.drawable.quickservice
                        else -> 0
                    },
                    when (it.get("Subindustry").toString()) {
                        "Bar / Lounge" -> R.drawable.bar_banner
                        "Cafe / Deli" -> R.drawable.cafe_banner
                        "Catering" -> R.drawable.catering_banner
                        "Coffee" -> R.drawable.coffee_banner
                        "Consumables" -> R.drawable.consumables_banner
                        "Full Serve" -> R.drawable.fullservice_banner
                        "Quick Serve" -> R.drawable.quickservice_banner
                        else -> 0
                    },
                    uid,
                    0
                )
            )
            uid++
        }
    }
}
