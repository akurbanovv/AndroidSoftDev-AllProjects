package com.example.dogpainter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.ImageView

class MainActivity : AppCompatActivity(), ButtonsFragment.OnFragmentInteractionListener{


    override fun onButtonClicked(color: Int) {
        var сanvas = findViewById<ImageView>(R.id.imageView_dog)
        сanvas.setBackgroundColor(color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
