package com.example.superhero


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.day_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class day : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.day_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val bundle = Bundle()

//        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<Button>(R.id.day_button).setOnClickListener {
//            listener?.onButtonClicked("Day")
//            bundle.putString("background", "day")
//        }
//        view.findViewById<Button>(R.id.night_button).setOnClickListener {
//            listener?.onButtonClicked("Night")
//            bundle.putString("background", "night")
//        }

        day_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("background", "day")
            findNavController().navigate(R.id.action_day_to_hero)

        }

        night_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("background", "night")
            findNavController().navigate(R.id.action_day_to_hero)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is day.OnFragmentInteractionListener){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    var listener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener{
        fun onButtonClicked(string: String)
    }


}
