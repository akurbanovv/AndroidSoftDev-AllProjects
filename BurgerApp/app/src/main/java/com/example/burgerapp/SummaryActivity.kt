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
import java.io.Serializable
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


        var profile = Profile("male", 112, 21, 6, 1)
        var bmr = CalcBMR(profile.gender, profile.weight, profile.age, profile.feet, profile.inches)
        var caloriesPerDay = bmr - totalCaloriess
        var suggestText = ""

        if (caloriesPerDay > 0) {
            suggestText = "You should eat $caloriesPerDay calories less"
        } else {
            suggestText = "You should eat " + (caloriesPerDay * (-1)) + " calories more"
        }

        update_button.setOnClickListener {

            val intent = Intent(this, ConfScreenActivity::class.java)
            intent.putExtra("profile", profile as Serializable)


            startActivity(intent)
        }

        genderValue_text.text = profile.gender
        weightValue_text.text = profile.weight.toString() + " lb"
        ageValue_text.text = profile.age.toString()
        heightValue_text.text = profile.feet.toString() + "' " + profile.inches.toString() + "''"

        bmrValue_text.text = bmr.toString()
        suggestValue_text.text = suggestText
    }

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>


    fun CalcBMR(Gender: String, Weight: Int, Age: Int, Feet: Int, Inches: Int): Int {
        var heightCm = (Feet + (Inches / 12.0)) * 30.48
        var weightKg = Weight * 0.45359237

        var bmr = 0.0

        if (Gender.toLowerCase() == "male") {
            bmr = 10.0 * weightKg + 6.25 * heightCm - 5.0 * Age.toDouble() + 5.0
        } else if (Gender.toLowerCase() == "female") {
            bmr = (10.0 * weightKg) + (6.25 * heightCm) - (5.0 * Age.toDouble()) - 161.0
        }

        return bmr.toInt()
    }


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

