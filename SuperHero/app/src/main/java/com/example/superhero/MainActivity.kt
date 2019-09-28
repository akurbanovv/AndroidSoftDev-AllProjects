package com.example.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.picture_fragment.*

class MainActivity : AppCompatActivity(){

//
//    override fun getBackground(background: String){
//        if (background == "day"){
//
//        }
//
//    }



//    override fun onButtonClicked(string: String){
//        val background = when (string){
//            "Day" -> R.drawable.day
//            "Night" -> R.drawable.night
//            else -> R.drawable.day
//        }
//        imageView.background(background)
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
