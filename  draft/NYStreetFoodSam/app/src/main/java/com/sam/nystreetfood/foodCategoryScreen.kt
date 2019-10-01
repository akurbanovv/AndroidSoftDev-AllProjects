package com.sam.nystreetfood


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_food_category_screen.*

/**
 * A simple [Fragment] subclass.
 */
class foodCategoryScreen : Fragment() {
    var countryList = ArrayList<restaurantEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_category_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCountries()

        viewManager = LinearLayoutManager(context)
        viewAdapter = RecyclerViewAdapter(countryList.toTypedArray()) {
            goToList(it)
        }

        catList.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }
    }

    fun goToList(country: String) {
        var bundle = Bundle()
        bundle.putString("country", country)
        findNavController().navigate(R.id.action_foodCategoryScreen_to_listScreen, bundle)
    }

    fun initCountries(): Unit {
        countryList.add(
            restaurantEntry(
                "",
                "",
                "American",
                "",
                "",
                "",
                "",
                R.drawable.food_american,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Chinese",
                "",
                "",
                "",
                "",
                R.drawable.food_chinese,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Dessert",
                "",
                "",
                "",
                "",
                R.drawable.food_dessert,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Fast Food",
                "",
                "",
                "",
                "",
                R.drawable.food_fastfood,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "French",
                "",
                "",
                "",
                "",
                R.drawable.food_french,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Italian",
                "",
                "",
                "",
                "",
                R.drawable.food_italian,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Japanese",
                "",
                "",
                "",
                "",
                R.drawable.food_japanese,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Mexican",
                "",
                "",
                "",
                "",
                R.drawable.food_mexico,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Pizza",
                "",
                "",
                "",
                "",
                R.drawable.food_pizza,
                0,
                0,
                0
            )
        )
        countryList.add(
            restaurantEntry(
                "",
                "",
                "Stakehouse",
                "",
                "",
                "",
                "",
                R.drawable.food_stakehouse,
                0,
                0,
                0
            )
        )
    }

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>

    class RecyclerViewAdapter(
        val CountryEntries: Array<restaurantEntry>,
        val onClick: (String) -> Unit
    ) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.categoryitem, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return CountryEntries.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(CountryEntries[position], onClick)
        }

        class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(restaurantEntry: restaurantEntry, onClick: (String) -> Unit) {
                viewItem.findViewById<ImageView>(R.id.catImg)
                    .setImageResource(restaurantEntry.image)
                viewItem.findViewById<TextView>(R.id.catTxt).text = restaurantEntry.type
                viewItem.setOnClickListener { onClick(restaurantEntry.type) }
            }
        }
    }


}
