package com.example.registerdemon


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_credential.*

/**
 * A simple [Fragment] subclass.
 */
class CredentialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credential, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        credential_next_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name_text.text.toString())
            bundle.putString("password", password_text.text.toString())

            findNavController().navigate(
                R.id.action_credentialFragment_to_informationFragment,
                bundle
            )
        }
    }
}
