package com.example.stockapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val selectedSymbol = intent.getStringExtra("symbol")
        val jsonString =
            resources.openRawResource(R.raw.sp500).bufferedReader().use { it.readText() }
        val stockArray = Stock.parseStockJson(jsonString)
        val stock = stockArray.find { it.symbol == selectedSymbol }

        name_text.text = stock.name
        price_text.text = stock.price.toString()
        info_text.text = "Earnings: ${stock?.earning}, " +  (${ stock?.low } / ${ stock?.high })"

    }
}
