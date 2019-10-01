package com.sam.burgerapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.foodmenu.view.*

class MainActivity : AppCompatActivity() {
    var menuList = ArrayList<menuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createMenuItems()

        //Assign the adapter to the layout.
        web_gridView.adapter = GridViewAdapter(menuList) {
            //Use intent to launch website.
            val openURL = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(openURL)
        }

        calculate_button.setOnClickListener {
            val checkedWebs = menuList.filter { it.checked }
//            Toast.makeText(this, checkedWebs.joinToString(",") { it.name }, Toast.LENGTH_LONG).show()
            val totalCals = calcCals()

            val intent = Intent(this, SummaryActivity::class.java)

            val menuNames = ArrayList<String>()
            val menuCals = ArrayList<Int>()
            val menuImgs = ArrayList<Int>()

            menuList.forEach {
                if (it.checked){
                    menuNames.add(it.name)
                    menuCals.add(it.calories)
                    menuImgs.add(it.image)
                }
            }

            intent.putStringArrayListExtra("menuNames", menuNames)
            intent.putIntegerArrayListExtra("menuCals", menuCals)
            intent.putIntegerArrayListExtra("menuImgs", menuImgs)

            startActivity(intent)
        }
    }

    fun createMenuItems(): Unit {
        menuList.add(
            menuItem(
                "Chicken Nuggets",
                R.drawable.chickennuggets,
                "https://www.bk.com/menu-item/chicken-nuggets",
                false,
                170
            )
        )
        menuList.add(
            menuItem(
                "French Fries",
                R.drawable.fries,
                "https://www.bk.com/menu-item/french-fries",
                false,
                380
            )
        )
        menuList.add(
            menuItem(
                "Chicken Jr.",
                R.drawable.crispychicken,
                "https://www.bk.com/menu-item/chicken-jr",
                false,
                450
            )
        )
        menuList.add(
            menuItem(
                "Spicy Chicken Jr.",
                R.drawable.spicycrispychicken,
                "https://www.bk.com/menu-item/spicy-chicken-jr",
                false,
                390
            )
        )
        menuList.add(
            menuItem(
                "Cheeseburger",
                R.drawable.herodoublecheeseburger,
                "https://www.bk.com/menu-item/cheeseburger",
                false,
                280
            )
        )
        menuList.add(
            menuItem(
                "Double Cheeseburger",
                R.drawable.herodoublecheeseburger,
                "https://www.bk.com/menu-item/double-cheeseburger",
                false,
                390
            )
        )
        menuList.add(
            menuItem(
                "Bacon Cheeseburger",
                R.drawable.baconcheeseburger,
                "https://www.bk.com/menu-item/bacon-cheeseburger",
                false,
                320
            )
        )
        menuList.add(
            menuItem(
                "Bacon Double Cheeseburger ",
                R.drawable.doublebaconcheeseburger,
                "https://www.bk.com/menu-item/bacon-double-cheeseburger",
                false,
                400
            )
        )
        menuList.add(
            menuItem(
                "Onion Rings",
                R.drawable.onionrings,
                "https://www.bk.com/menu-item/onion-rings",
                false,
                150
            )
        )
        menuList.add(
            menuItem(
                "Vanilla Soft Serve",
                R.drawable.vanillasoftserve,
                "https://www.bk.com/menu-item/vanilla-soft-serve",
                false,
                140
            )
        )
        menuList.add(
            menuItem(
                "Coca-Cola",
                R.drawable.cocacola,
                "https://www.bk.com/menu-item/coca-cola%C2%AE",
                false,
                140
            )
        )
        menuList.add(
            menuItem(
                "Diet Coke",
                R.drawable.coke,
                "https://www.bk.com/menu-item/diet-coke",
                false,
                0
            )
        )
        menuList.add(
            menuItem(
                "Dr Pepper",
                R.drawable.drpep,
                "https://www.bk.com/menu-item/dr-pepper",
                false,
                140
            )
        )
        menuList.add(
            menuItem(
                "Sprite",
                R.drawable.sprite,
                "https://www.bk.com/menu-item/sprite",
                false,
                140
            )
        )
        menuList.add(
            menuItem(
                "Frozen Fanta Cherry ICEE",
                R.drawable.icee,
                "https://www.bk.com/menu-item/frozen-fanta-cherry-icee",
                false,
                120
            )
        )

    }

    fun calcCals(): Int {
        var totalCals = 0
        val checkedWebs = menuList.filter { it.checked }

        checkedWebs.forEach {
            totalCals += it.calories
        }

        return totalCals
    }

    //Adapter class to generate grid items
    class GridViewAdapter(val menuList: ArrayList<menuItem>, val click: (String) -> Unit) :
        BaseAdapter() {

        //Return how many items in the grid
        override fun getCount(): Int {
            return menuList.size
        }

        //Return each item
        override fun getItem(position: Int): Any {
            return menuList[position]
        }

        //Return the id for each item. Use the position as id
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val menuEntry = this.menuList[position]

            var foodItemView =
                LayoutInflater.from(parent?.context).inflate(R.layout.foodmenu, parent, false)

            foodItemView.food_name.text = menuEntry.name

            foodItemView.calCount.text = menuEntry.calories.toString()

            foodItemView.itemPicture.setImageResource(menuEntry.image)

            foodItemView.itemPicture.setOnClickListener {
                click(menuEntry.url)
            }

            foodItemView.food_name.setOnClickListener {
                click(menuEntry.url)
            }

            foodItemView.food_checkbox.setOnCheckedChangeListener { compoundButton, b ->
                menuEntry.checked = b
            }
            return foodItemView
        }
    }
}
