package com.example.burgerapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_item.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var menuItems = ArrayList<MenuItem>()
        menuItems.add(
            MenuItem(
                "Chicken Jr",
                "450",
                R.drawable.crispychicken,
                "https://www.bk.com/menu-item/chicken-jr",
                false))

        menuItems.add(
            MenuItem(
                "Chicken Nuggets",
                "170",
                R.drawable.chickennuggets,
                "https://www.bk.com/menu-item/chicken-nuggets",
                false))

        menuItems.add(
            MenuItem(
                "French Fries",
                "380",
                R.drawable.fries,
                "https://www.bk.com/menu-item/french-fries",
                false))

        menuItems.add(
            MenuItem(
                "Spicy Chicken Jr.",
                "390",
                R.drawable.spicycrispychicken,
                "https://www.bk.com/menu-item/spicy-chicken-jr",
                false))

        menuItems.add(
            MenuItem(
                "Cheeseburger",
                "280",
                R.drawable.herocheeseburger,
                "https://www.bk.com/menu-item/cheeseburger",
                false))

        menuItems.add(
            MenuItem(
                "Double Cheeseburger",
                "390",
                R.drawable.herodoublecheeseburger,
                "https://www.bk.com/menu-item/double-cheeseburger",
                false))

        menuItems.add(
            MenuItem(
                "Bacon Cheeseburger",
                "320",
                R.drawable.baconcheeseburger,
                "https://www.bk.com/menu-item/bacon-cheeseburger",
                false))

        menuItems.add(
            MenuItem(
                "Bacon Double Cheeseburger",
                "400",
                R.drawable.doublebaconcheeseburger,
                "https://www.bk.com/menu-item/bacon-double-cheeseburger",
                false))

        menuItems.add(
            MenuItem(
                "Onion Rings",
                "150",
                R.drawable.onionrings,
                "https://www.bk.com/menu-item/onion-rings",
                false))

        menuItems.add(
            MenuItem(
                "Vanilla Soft Serve",
                "140",
                R.drawable.vanillasoftserve,
                "https://www.bk.com/menu-item/vanilla-soft-serve",
                false))

        menuItems.add(
            MenuItem(
                "Coca Cola",
                "140",
                R.drawable.cocacola,
                "https://www.bk.com/menu-item/coca-cola®",
                false))

        menuItems.add(
            MenuItem(
                "Diet Coke",
                "0",
                R.drawable.dietcoke,
                "https://www.bk.com/menu-item/diet-coke",
                false))

        menuItems.add(
            MenuItem(
                "Dr Pepper",
                "140",
                R.drawable.drpep,
                "https://www.bk.com/menu-item/dr-pepper",
                false))

        menuItems.add(
            MenuItem(
                "Sprite",
                "140",
                R.drawable.sprite,
                "https://www.bk.com/menu-item/sprite",
                false))

        menuItems.add(
            MenuItem(
                "Frozen Fanta Cherry ICEE®",
                "110",
                R.drawable.icee,
                "https://www.bk.com/menu-item/frozen-fanta-cherry-icee",
                false))

        menu_gridView.adapter = GridViewAdapter(menuItems){
            val openURL = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(openURL)
        }

        calc_button.setOnClickListener {
            var itemNamesToPass = ArrayList<String>()
            var itemCaloriesToPass = ArrayList<String>()
            var itemImagesToPass = ArrayList<Int>()

            menuItems.forEach {
                if (it.checked){
                    itemNamesToPass.add(it.name)
                    itemCaloriesToPass.add(it.cal)
                    itemImagesToPass.add(it.image)
                }
            }

            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("itemsNames",itemNamesToPass)
            intent.putExtra("itemsCalories", itemCaloriesToPass)
            intent.putExtra("itemsImages", itemImagesToPass)

            startActivity(intent)
        }


    }


    class GridViewAdapter(val menuList: ArrayList<MenuItem>, val click: (String) -> Unit):
        BaseAdapter() {

            override fun getCount(): Int {
                return menuList.size
            }

            override fun getItem(position: Int): Any {
                return menuList[position]
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val menuItem = this.menuList[position]
                var menuItemView = LayoutInflater.from(parent?.context).inflate(R.layout.menu_item, parent, false)

                menuItemView.item_name.text = menuItem.name
                menuItemView.item_cal.text = menuItem.cal
                menuItemView.item_image.setImageResource(menuItem.image)

                menuItemView.item_name.setOnClickListener {
                    click(menuItem.url)
                }

                menuItemView.item_image.setOnClickListener {
                    click(menuItem.url)
                }

                menuItemView.item_checkbox.setOnCheckedChangeListener { compoundButton, b ->
                    menuItem.checked = b
                }

                return menuItemView
            }

        }
}

