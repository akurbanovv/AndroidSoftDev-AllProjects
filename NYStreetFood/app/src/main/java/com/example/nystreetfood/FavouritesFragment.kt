package com.example.nystreetfood


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesFragment : Fragment() {
    var restaurantList = ArrayList<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        restaurantList = listener.getRestaurantObject()
        var restaurantListToShow = restaurantList.filter { it.rating > 0 }.toTypedArray()
        restaurantListToShow.sortByDescending { it.rating }

        viewManager = LinearLayoutManager(context)
        viewAdapter = RecyclerViewAdapter(restaurantListToShow) {
            showDetail(it)
        }

        favList.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }
    }

    fun showDetail(Restaurant: Restaurant) {
        var bundle = Bundle()
        bundle.putInt("uid", Restaurant.uid)
        findNavController().navigate(R.id.action_savedRestaurantScreen_to_detailScreen, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    lateinit var listener: OnFragmentInteractionListener

    interface OnFragmentInteractionListener {
        fun getRestaurantObject(): ArrayList<Restaurant>
    }

    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>

    class RecyclerViewAdapter(
        val Restaurants: Array<Restaurant>,
        val onClick: (Restaurant) -> Unit
    ) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_favourites, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return Restaurants.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(Restaurants[position], onClick)
        }

        class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(Restaurant: Restaurant, onClick: (Restaurant) -> Unit) {
                viewItem.findViewById<TextView>(R.id.name).text = Restaurant.name
                viewItem.findViewById<RatingBar>(R.id.rating).rating =
                    Restaurant.rating.toFloat()
                viewItem.setOnClickListener { onClick(Restaurant) }
            }
        }
    }
}
