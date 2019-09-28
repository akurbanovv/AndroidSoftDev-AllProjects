package com.example.registerdemon


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login_text.text = listener.getUserProfile()

        edit_button.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_credentialFragment)
        }
    }

    lateinit var listener: OnFragmentInteractionListner

    interface OnFragmentInteractionListner {
        fun getUserProfile(): String
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListner) {
            listener = context
        }
    }




}
