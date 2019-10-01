package com.example.streetfoodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStream
import kotlin.collections.ArrayList
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.opencsv.CSVReader
import com.opencsv.CSVReaderHeaderAware
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var uid = 0
    var restaurantList = ArrayList<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseRestaurants()

    }

    override fun getRestaurantObject(): ArrayList<Restaurant> {
        return restaurantList
    }


    private fun parseRestaurants(){
        val reader =
            CSVReaderHeaderAware(resources.openRawResource(R.raw.data).bufferedReader())
        val resultList = mutableListOf<Map<String, String>>()
        var line = reader.readMap()
        while (line != null) {
            resultList.add(line)
            line = reader.readMap()
        }

        resultList.forEach {
            restaurantList.add(
                Restaurant(
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






