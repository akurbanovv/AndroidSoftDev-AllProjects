package com.example.csi210.coffeeorder


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_cart.*

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    lateinit var coffeeViewModel: CoffeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coffeeViewModel = activity?.run {
            ViewModelProviders.of(this).get(coffeeViewModel::class.java)
        } ?: throw Exception("Invalid Activity!")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val coffeeList = listener.getCart()
        viewManager = LinearLayoutManager(context)
        viewAdapter = RecyclerViewAdapter(ArrayList<Coffee>())
        coffee_recycler.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        coffeeViewModel.coffeeList.observe(this, Observer {
            viewAdapter.list = it
            viewAdapter.notifyDataSetChanged()
        })


        ItemTouchHelper(SwiperHelper()).attachToRecyclerView(
            coffee_recycler
        )
    }


    lateinit var listener: OnFragmentInteractionListener

    interface OnFragmentInteractionListener {
        fun getCart(): ArrayList<Coffee>
        fun updateCart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
            listener = context
    }

    inner class SwiperHelper() : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            coffeeViewModel.removeCoffee(viewHolder.adapterPosition)
            viewAdapter.notifyDataSetChanged()
            listener.updateCart()
        }
    }

    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var viewManager: RecyclerView.LayoutManager

    class RecyclerViewAdapter(var list: ArrayList<Coffee>) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            var viewItem =
                LayoutInflater.from(parent.context).inflate(R.layout.coffee_item, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            return holder.bind(list[position])
        }

        class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(coffee: Coffee) {
                viewItem.run {
                    findViewById<TextView>(R.id.name_text).text = coffee.name
                    findViewById<TextView>(R.id.size_text).text = coffee.size
                    findViewById<TextView>(R.id.qty_text).text = coffee.qty.toString()
                }
            }
        }
    }
}
