package com.example.burgerapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_summary.*
import java.util.*
import kotlin.collections.ArrayList

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var menuItemsChecked = ArrayList<MenuItem>()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val itemsNames = intent.getStringArrayListExtra("itemsNames")
        val itemsCalories = intent.getStringArrayListExtra("itemsCalories")
        val itemsImages = intent.getIntegerArrayListExtra("itemsImages")

        var n = itemsNames.size - 1

        for (i in 0..n) {
            menuItemsChecked.add(
                MenuItem(
                    itemsNames[i],
                    itemsCalories[i],
                    itemsImages[i],
                    "",
                    true
                )
            )
        }

        var totalCaloriess = 0
        val checkedItems = menuItemsChecked.filter { it.checked }

        checkedItems.forEach {
            totalCaloriess += it.cal.toInt()
        }

        caloriesValue_text.text = totalCaloriess.toString()

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(menuItemsChecked.filter { it.checked }.toTypedArray())

        items_recyclerView.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }

        var profile = Profile("male", "190 lb", "22", "6'11''")



        update_button.setOnClickListener {
            val intent = Intent(this, ConfScreen::class.java)
            startActivity(intent)
        }

        genderValue_text.text = profile.gender
        weightValue_text.text = profile.weight
        ageValue_text.text = profile.age
        heightValue_text.text = profile.height
    }

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>


    // fun CalcBMR(Int: )

    class RecyclerViewAdapter(
        val menuData: Array<MenuItem>
    ) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemlist_summary, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return menuData.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(menuData[position])
        }

        class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(Item: MenuItem) {
                viewItem.findViewById<ImageView>(R.id.item_image_insum).setImageResource(Item.image)
                viewItem.findViewById<TextView>(R.id.item_name_insum).text = Item.name
                viewItem.findViewById<TextView>(R.id.item_cals_insum).text = Item.cal
            }
        }
    }
}

