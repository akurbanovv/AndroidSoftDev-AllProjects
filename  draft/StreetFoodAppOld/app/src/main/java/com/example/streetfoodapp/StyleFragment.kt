package com.example.streetfoodapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_style.*
import kotlinx.android.synthetic.main.style_grid.*
import kotlinx.android.synthetic.main.style_grid.view.*


class StyleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_style, container, false)
    }

    var styleItems = ArrayList<Style>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        styles_gridView.adapter = GridViewAdapter(styleItems)


//        styleName_textView.setOnClickListener {
//            findNavController().navigate(R.id.action_styleFragment_to_listFragment)
//        }
//        style_imageView.setOnClickListener{
//            findNavController().navigate(R.id.action_styleFragment_to_listFragment)
//        }


    }

    class GridViewAdapter(val styleList: ArrayList<Style>) : BaseAdapter() {
        override fun getCount(): Int {
            return styleList.size
        }

        override fun getItem(position: Int): Any {
            return styleList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val styleItem = this.styleList[position]
            var styleItemView =
                LayoutInflater.from(parent?.context).inflate(R.layout.style_grid, parent, false)

            styleItemView.styleName_textView.text = styleItem.name
            styleItemView.style_imageView.setImageResource(styleItem.image)



            return styleItemView
        }
    }


}
