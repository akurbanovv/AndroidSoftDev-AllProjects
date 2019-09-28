package com.example.dogpainter


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A simple [Fragment] subclass.
 */
class ButtonsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_chocolate).setOnClickListener {
            listener?.onButtonClicked(Color.rgb(210,105,30))
        }
        view.findViewById<Button>(R.id.button_golden).setOnClickListener {
            listener?.onButtonClicked(Color.rgb(218,165,32))
        }
    }

    var listener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener{
        fun onButtonClicked(color: Int)
    }



}
