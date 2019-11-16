package com.example.csi210.coffeeorder


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_coffee.*

/**
 * A simple [Fragment] subclass.
 */
class CoffeeFragment : Fragment() {

    val coffeeTypes = arrayOf("Espresso", "Macchiato", "Cappuccino", "Latte")
    val coffeeSizes = arrayOf("Small", "Medium", "Large")

    lateinit var coffeeViewModel: CoffeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coffeeViewModel = activity?.run {
            ViewModelProviders.of(this).get(CoffeeViewModel::class.java)
        } ?: throw Exception("Invalid Activity!")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coffee, container, false)
    }

    override fun onPause() {
        super.onPause()
        coffeeViewModel.spinnerPosition.value = coffee_spinner.selectedItemPosition
        coffeeViewModel.radioButtonID.value = coffee_radioGroup.checkedRadioButtonId
        coffeeViewModel.quantityString.value = coffee_qty_text.text.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item,
            coffeeTypes
        )
        coffee_spinner.adapter = adapter

        confirm_button.setOnClickListener {
            //            val coffee = Coffee(coffeeTypes[coffee_spinner.selectedItemPosition], "Large", 1)
            //            listener.addCoffee(coffee)
            coffeeViewModel.addCoffee(createCoffee())
            listener.updateCart()
        }

        coffeeViewModel.spinnerPosition.observe(this, Observer {
            coffee_spinner.setSelection(it)
        })

        coffeeViewModel.radioButtonID.observe(this, Observer {
            coffee_radioGroup.check(it)
        })

        coffeeViewModel.quantityString.observe(this, Observer {
            coffee_qty_text.setText(it)
        })
    }

    fun createCoffee(): Coffee {
        var type = coffeeTypes[coffee_spinner.selectedItemPosition]
        var size = when (coffee_radioGroup.checkedRadioButtonId) {
            small_radioButton.id -> coffeeSizes[0]
            medium_radioButton.id -> coffeeSizes[1]
            large_radioButton.id -> coffeeSizes[2]
            else -> coffeeSizes[0]
        }

        var quantity = coffee_qty_text.text.toString().toInt()
        return Coffee(type, size, quantity)
    }

    lateinit var listener: OnFragmentInteractionListener

    interface OnFragmentInteractionListener {
        fun addCoffee(coffee: Coffee)
        fun updateCart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener)
            listener = context
    }
}
