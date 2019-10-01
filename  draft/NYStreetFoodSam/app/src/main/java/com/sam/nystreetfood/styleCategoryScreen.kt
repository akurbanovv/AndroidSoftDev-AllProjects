package com.sam.nystreetfood


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_style_category_screen.*
import kotlinx.android.synthetic.main.stylebox.view.*

/**
 * A simple [Fragment] subclass.
 */
class styleCategoryScreen : Fragment() {
    var styleList = ArrayList<restaurantEntry>()
    var isGridLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_style_category_screen, container, false)
    }

    fun initStyles(): Unit {
        styleList.add(
            restaurantEntry(
                "",
                "Bar / Lounge",
                "",
                "",
                "",
                "",
                "",
                R.drawable.bar,
                0,
                0,
                0
            )
        )
        styleList.add(
            restaurantEntry(
                "",
                "Café / Deli",
                "",
                "",
                "",
                "",
                "",
                R.drawable.cafe,
                0,
                0,
                0
            )
        )
        styleList.add(
            restaurantEntry(
                "",
                "Catering",
                "",
                "",
                "",
                "",
                "",
                R.drawable.catering,
                0,
                0,
                0
            )
        )
        styleList.add(
            restaurantEntry(
                "",
                "Coffee",
                "",
                "",
                "",
                "",
                "",
                R.drawable.coffee,
                0,
                0,
                0
            )
        )
        styleList.add(
            restaurantEntry(
                "",
                "Consumables",
                "",
                "",
                "",
                "",
                "",
                R.drawable.consumables,
                0,
                0,
                0
            )
        )
        styleList.add(
            restaurantEntry(
                "",
                "Full Serve",
                "",
                "",
                "",
                "",
                "",
                R.drawable.fullservice,
                0,
                0,
                0
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isGridLoaded) {
            isGridLoaded = true
            initStyles()
        }

        styleGrid.adapter = GridViewAdapter(styleList) {
            var bundle = Bundle()
            bundle.putString("style", it)
            findNavController().navigate(R.id.action_styleCategoryScreen_to_listScreen, bundle)
        }
    }

    class GridViewAdapter(val styleList: ArrayList<restaurantEntry>, val click: (String) -> Unit) :
        BaseAdapter() {

        //Return how many items in the grid
        override fun getCount(): Int {
            return styleList.size
        }

        //Return each item
        override fun getItem(position: Int): Any {
            return styleList[position]
        }

        //Return the id for each item. Use the position as id
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val styleEntry = this.styleList[position]

            var styleEntryList =
                LayoutInflater.from(parent?.context).inflate(R.layout.stylebox, parent, false)

            styleEntryList.styleTxt.text = styleEntry.kind

            styleEntryList.styleImg.setImageResource(styleEntry.image)

            styleEntryList.styleEntry.setOnClickListener {
                if (styleEntry.kind == "Café / Deli") {
                    click("Cafe / Deli")
                } else {
                    click(styleEntry.kind)
                }
            }

            return styleEntryList
        }
    }


}
