package com.example.shippingratecalc

import java.lang.Integer.parseInt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    class Item() {
        var name = ""
        var weight = 0
    }

    var itemList: ArrayList<Item>? = null

    val itemPrices = intArrayOf(5, 4, 3)
    // val itemPrices = Array(size: 3){ 5 - it }

    var couponApplied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = ArrayList()

        add_button.setOnClickListener {
            addItem(name_text.text.toString(), weight_text.text.toString())
            showItem()
        }

        coupon_checkbox.setOnCheckedChangeListener { compoundButton, b ->
            couponApplied = b
            showItem()
        }

        clear_button.setOnClickListener {
            itemList?.clear()
            showItem()
        }


    }

    fun addItem(name: String, weight: String) {
        val item = Item()
        item.name = name
        item.weight = if (weight != "") parseInt(weight) else 0
        itemList?.add(item)
    }

    fun showItem() {
        val getItemPrice: (Int) -> Double = {
            val discount = if (couponApplied) 0.9 else 0.1
            it * discount * when (it) {
                in 0..10 -> itemPrices[0]
                in 11..100 -> itemPrices[1]
                else -> itemPrices[2]
            }
        }

        var sumWeight = 0
        var sumPrice = 0.0

        itemList?.run {
            for (i in this) {
                sumPrice += getItemPrice(i.weight)
            }
            sumWeight = this.sumBy {
                it.weight
            }
        }

        var information = "Total weight: ${sumWeight}, total price: ${sumPrice}"

        itemList?.forEach {
            information += "name:${it.name}, weight:${it.weight}, " +
                    "price:${getItemPrice(it.weight)}\n"
        }

        info_textView.text = information
    }

}
