package com.example.drumapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*



/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    lateinit var viewModel: DrumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = activity?.run{
            ViewModelProviders.of(this).get(DrumViewModel::class.java)
        }?: throw Exception("activity invalid")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compose_button.setOnClickListener {

            viewModel.curRecName.value = name_editText.text.toString()

            val bundle = Bundle()
            bundle.putString("name", name_editText.text.toString())

            if (!name_editText.text.isEmpty()){
                findNavController().navigate(R.id.action_homeFragment_to_drumFragment, bundle)
            } else {
                Toast.makeText(getActivity(),"Put your name", Toast.LENGTH_LONG).show()
            }


        }

        playlist_button.setOnClickListener {

            viewModel.curRecName.value = name_editText.text.toString()

            val bundle = Bundle()
            bundle.putString("name", name_editText.text.toString())

            findNavController().navigate(R.id.action_homeFragment_to_recordFragment)
        }
    }
}
