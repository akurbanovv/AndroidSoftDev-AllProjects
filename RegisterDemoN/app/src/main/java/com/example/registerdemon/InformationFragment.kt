package com.example.registerdemon


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_information.*

/**
 * A simple [Fragment] subclass.
 */
class InformationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        complete_button.setOnClickListener {
            listener.saveInformation(
                arguments?.getString("username").toString(),
                arguments?.getString("password").toString(),
                dob_text.text.toString()
            )

            findNavController().navigate(R.id.action_informationFragment_to_profileFragment)
        }
    }

    lateinit var listener: OnFragmentInteractionListener

    interface OnFragmentInteractionListener {
        fun saveInformation(u: String, p: String, d: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }
}
