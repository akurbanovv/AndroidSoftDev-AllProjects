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
            showItems()
        }

        coupon_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            couponApplied = b
            showItems()
        }

        clear_button.setOnClickListener {
            itemList?.clear()
            showItems()
        }
    }

    fun addItem(name: String, weight: String) {
        val item = Item()
        item.name = name
        item.weight = if (weight == "") 0 else parseInt(weight)
        itemList?.add(item)
    }

    fun showItems() {
        val getItemPrice: (Int) -> Double = {
            val discount = if (couponApplied) 0.9 else 0.1
            it * discount * when (it) {
                in 0..10 -> itemPrices[0]
                in 11..100 -> itemPrices[1]
                else -> itemPrices[2]
            }
        }

        var sumCost = 0.0
        var sumWeight = 0

        itemList?.run {
            for (i in this){
                sumCost += getItemPrice(i.weight)
            }
            sumWeight = this.sumBy {
                it.weight }
        }

        var information = "Total weight $sumWeight   Total cost $sumCost\n"

        itemList?.forEach {
            information += "item:${it.name}, weight: ${it.weight}lb, rate: ${getItemPrice(it.weight)}\n"
        }

        info_textview.text = information
    }
}
