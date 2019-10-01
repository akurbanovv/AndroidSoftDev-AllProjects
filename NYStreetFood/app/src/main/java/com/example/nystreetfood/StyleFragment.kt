package com.example.nystreetfood


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_style.*
import kotlinx.android.synthetic.main.item_style.view.*


class StyleFragment : Fragment() {
    var styleItems = ArrayList<Style>()
    var isGridLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_style, container, false)
    }

    private fun addStyles() {
        styleItems.add(
            Style("Bar / Lounge", R.drawable.bar)
        )
        styleItems.add(
            Style("Café / Deli", R.drawable.cafe)
        )
        styleItems.add(
            Style("Catering", R.drawable.catering)
        )
        styleItems.add(
            Style("Coffee", R.drawable.coffee)
        )
        styleItems.add(
            Style("Consumables", R.drawable.consumables)
        )
        styleItems.add(
            Style("Full Serve", R.drawable.fullservice)
        )
        styleItems.add(
            Style("Quick Serve", R.drawable.quickservice)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isGridLoaded) {
            isGridLoaded = true
            addStyles()
        }
        // here

        style_grid.adapter = GridViewAdapter(styleItems) {
            var bundle = Bundle()
            bundle.putString("style", it)
            findNavController().navigate(R.id.action_styleCategoryScreen_to_listScreen, bundle)
        }
    }

    class GridViewAdapter(val styleItems: ArrayList<Style>, val click: (String) -> Unit) :
        BaseAdapter() {

        override fun getCount(): Int {
            return styleItems.size
        }

        override fun getItem(position: Int): Any {
            return styleItems[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val styleEntry = this.styleItems[position]

            var styleEntryList =
                LayoutInflater.from(parent?.context).inflate(R.layout.item_style, parent, false)

            styleEntryList.style_text.text = styleEntry.name

            styleEntryList.style_img.setImageResource(styleEntry.image)
            
            styleEntryList.styleEntry.setOnClickListener {
                if (styleEntry.name == "Café / Deli") {
                    click("Cafe / Deli")
                } else {
                    click(styleEntry.name)
                }
                // here
            }

            return styleEntryList
        }
    }


}
