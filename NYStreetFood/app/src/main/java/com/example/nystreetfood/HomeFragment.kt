package com.example.nystreetfood


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        all_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_listScreen)
        }
        style_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_styleCategoryScreen)
        }
        food_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_foodCategoryScreen)
        }

        var bundle = Bundle()
        bundle.putInt("uid", -1)
        random_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_detailScreen, bundle)
        }



        // ???
        all.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_listScreen)
        }
        style.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_styleCategoryScreen)
        }
        food.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_foodCategoryScreen)
        }
        random.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_detailScreen, bundle)
        }
    }

}
