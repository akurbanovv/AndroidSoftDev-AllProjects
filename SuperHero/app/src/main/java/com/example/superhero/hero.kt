package com.example.superhero


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_hero.*

/**
 * A simple [Fragment] subclass.
 */
class hero : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = Bundle()
//        super.onViewCreated(view, savedInstanceState)
//
//
//        view.findViewById<Button>(R.id.super_button).setOnClickListener {
//            listener?.onButtonClicked("Superman")
//            bundle.putString("hero", "superman")
//        }
//        view.findViewById<Button>(R.id.bat_button).setOnClickListener {
//            listener?.onButtonClicked("Batman")
//            bundle.putString("hero", "bat")
//        }
//        view.findViewById<Button>(R.id.).setOnClickListener {
//            listener?.onButtonClicked("Batman")
//            bundle.putString("hero", "bat")
//        }


        super_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("hero", "superman")
            findNavController().navigate(R.id.action_hero_to_picture)
        }
        bat_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("hero", "batman")
            findNavController().navigate(R.id.action_hero_to_picture)
        }
        iron_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("hero", "ironman")
            findNavController().navigate(R.id.action_hero_to_picture)
        }

    }


}
