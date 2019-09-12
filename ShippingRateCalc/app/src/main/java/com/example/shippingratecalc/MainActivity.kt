package com.example.shippingratecalc

import java.lang.Integer.parseInt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    class Item {
        var name = ""
        var weight = 0
    }

    var itemList: ArrayList<Item>? = null
    val itemPrices = Array(3) { 5.0 - it }
    var couponApplied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_button.setOnClickListener {
            addItem(item_name.text.toString(), item_weight.text.toString())
            Log.d("test", "add button checked")
        }

        showItems()
    }

    fun addItem(name: String, weight: String) {
        val item = Item()
        item.name = name
        item.weight = if (weight == "") 0 else parseInt(weight)
        itemList?.add(item)
    }

    fun showItems() {

        var information = ""
        itemList?.forEach {
            information += "item:${it.name}, weight: ${it.weight}lb"
        }

        info_textview.text = information
    }

    fun getItemPrice: (Int) -> Double = { weight: Int ->
        val discount = if (couponApplied) 0.9 else 1.0
        var pr = 0.0
        discount * when (weight) {
            in 0..10 -> weight * itemPrices[0]
            in 11..100 -> weight * itemPrices[1]
            else -> weight * itemPrices[2]
        }
    }


}
