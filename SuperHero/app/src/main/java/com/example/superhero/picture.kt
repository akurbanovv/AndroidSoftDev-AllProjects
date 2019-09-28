package com.example.superhero


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.picture_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class picture : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.picture_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments?.getString("hero").toString() == "superman"){
            hero_image.setImageResource(R.drawable.superman)
            background_image.setImageResource(R.drawable.superman)
        }
    }
}
