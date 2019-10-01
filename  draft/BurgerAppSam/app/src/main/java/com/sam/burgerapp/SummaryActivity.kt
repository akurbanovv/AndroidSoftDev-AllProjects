package com.sam.burgerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {
    var menuList = ArrayList<menuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        var menuNames = intent.getStringArrayListExtra("menuNames")
        var menuCals = intent.getIntegerArrayListExtra("menuCals")
        var menuImgs = intent.getIntegerArrayListExtra("menuImgs")

        val menuLength = menuNames.size

        for (i in 0..menuLength - 1) {
            menuList.add(
                menuItem(menuNames[i], menuImgs[i], "", true, menuCals[i])
            )
        }

        var totalCals = 0
        val checkedWebs = menuList.filter { it.checked }

        checkedWebs.forEach {
            totalCals += it.calories
        }

        calField.text = totalCals.toString()

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(menuList.filter { it.checked }.toTypedArray())

        gotFoodItems.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }

        update_button.

    }

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>

    class RecyclerViewAdapter(
        val menuData: Array<menuItem>
    ) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context).inflate(R.layout.summaryitem, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return menuData.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(menuData[position])
        }

        class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(menuItem: menuItem) {
                viewItem.findViewById<ImageView>(R.id.itemPicture)
                    .setImageResource(menuItem.image) // image will go here
                viewItem.findViewById<TextView>(R.id.itemName).text = menuItem.name
                viewItem.findViewById<TextView>(R.id.itemCal).text = menuItem.calories.toString()
            }
        }
    }
}

