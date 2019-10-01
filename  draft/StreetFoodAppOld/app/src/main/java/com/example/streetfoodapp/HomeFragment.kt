package com.example.streetfoodapp


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
        style_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_styleFragment)
        }
        all_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }

        food_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_foodFragment)
        }

        random_button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }
}
