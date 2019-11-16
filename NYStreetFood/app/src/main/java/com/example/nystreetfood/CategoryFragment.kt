package com.example.nystreetfood


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
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {
    var countryList = ArrayList<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        addCategories()

        viewManager = LinearLayoutManager(context)
        viewAdapter = RecyclerViewAdapter(countryList.toTypedArray()) {
            goToListScreen(it)
        }

        category_list.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }
    }

    fun goToListScreen(country: String) {
        var bundle = Bundle()
        bundle.putString("country", country)
        findNavController().navigate(R.id.action_foodCategoryScreen_to_listScreen, bundle)
    }

    private fun addCategories() {
        countryList.add(
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
            Restaurant(
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
        val countryEntries: Array<Restaurant>,
        val onClick: (String) -> Unit
    ) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return countryEntries.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(countryEntries[position], onClick)
        }

        class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(Restaurant: Restaurant, onClick: (String) -> Unit) {
                viewItem.findViewById<ImageView>(R.id.category_image)
                    .setImageResource(Restaurant.image)
                viewItem.findViewById<TextView>(R.id.category_text).text = Restaurant.type
                viewItem.setOnClickListener { onClick(Restaurant.type) }
            }
        }
    }


}
