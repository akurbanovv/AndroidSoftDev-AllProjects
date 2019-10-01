package com.example.streetfoodapp


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
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class FoodFragment : Fragment() {

    var categoryList = ArrayList<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // super.onViewCreated(view, savedInstanceState)
        addCategories()

        viewManager = LinearLayoutManager(context)
        viewAdapter = RecyclerViewAdapter(categoryList.toTypedArray()) {
            var bundle = Bundle()
            bundle.putString("category", it)
            findNavController().navigate(R.id.action_foodFragment_to_listFragment, bundle)
        }
    }

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>

    class RecyclerViewAdapter(
        val CategoryEntries: Array<Restaurant>,
        val onClick: (String) -> Unit
    ) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.food_category, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return CategoryEntries.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(CategoryEntries[position], onClick)
        }

        class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(category: Restaurant, onClick: (String) -> Unit) {
                viewItem.findViewById<ImageView>(R.id.category_imageView).setImageResource(category.image)
                viewItem.findViewById<TextView>(R.id.category_textView).text = category.name
                viewItem.setOnClickListener { onClick(category.name) }
            }
        }
    }

    private fun addCategories() {
        categoryList.add(
            Restaurant("", "", "American", "", "", "", "", R.drawable.food_american, 0, 0, 0)
        )
//        categoryList.add(
//            Category("Chinese", R.drawable.food_chinese)
//        )
//        categoryList.add(
//            Category("Stakehouse", R.drawable.food_stakehouse)
//        )
//        categoryList.add(
//            Category("Fastfood", R.drawable.food_fastfood)
//        )
//        categoryList.add(
//            Category("French", R.drawable.food_french)
//        )
//        categoryList.add(
//            Category("Italian", R.drawable.food_italian)
//        )
//        categoryList.add(
//            Category("Dessert", R.drawable.food_dessert)
//        )
//        categoryList.add(
//            Category("Japanese", R.drawable.food_japanese)
//        )
//        categoryList.add(
//            Category("Mexico", R.drawable.food_mexico)
//        )
//        categoryList.add(
//            Category("Pizza", R.drawable.food_pizza)
//        )
    }
}
