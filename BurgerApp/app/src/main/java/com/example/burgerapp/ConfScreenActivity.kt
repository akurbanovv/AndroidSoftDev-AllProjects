package com.example.burgerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_update.*

class ConfScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val profile = intent.extras?.get("profile") as Profile

        ageUpdate_text.setText(profile.age.toString())
        weightUpdate_text.setText(profile.weight.toString())
        feetUpdate_text.setText((profile.feet.toString()))
        inchesUpdate_text.setText((profile.inches.toString()))

        male_button.isClickable

    }

}