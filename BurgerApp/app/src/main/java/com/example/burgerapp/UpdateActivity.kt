package com.example.burgerapp

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_summary.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_update.ageUpdate_text
import kotlinx.android.synthetic.main.activity_update.feetUpdate_text
import kotlinx.android.synthetic.main.activity_update.weightUpdate_text
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)


        var profile = intent.extras?.get("profile") as Profile

        print(profile.age)

        ageUpdate_text.setText(profile.age.toString())
        weightUpdate_text.setText(profile.weight.toString())
        feetUpdate_text.setText((profile.feet.toString()))
        inchesUpdate_text.setText((profile.inches.toString()))

        profile.age = 99

        if (profile.gender == "male") {
            buttonsUpdate_radioGroup.check(R.id.male_button)
        } else if (profile.gender == "female"){
            buttonsUpdate_radioGroup.check(R.id.female_button)
        }

        reupdate_button.setOnClickListener {
            profile.age = ageUpdate_text.text.toString().toInt()
            profile.gender = onRadioButtonClicked(buttonsUpdate_radioGroup)
            profile.weight = weightUpdate_text.text.toString().toInt()
            profile.feet = feetUpdate_text.text.toString().toInt()
            profile.inches = inchesUpdate_text.text.toString().toInt()

            var intent = intent.apply {
                intent.putExtra("profile", profile)
            }
            setResult(1, intent)
            finish()
        }
    }

    fun onRadioButtonClicked(view: RadioGroup): String{
        if (buttonsUpdate_radioGroup.checkedRadioButtonId == R.id.female_button){
            return "female"
        }
        return "male"
    }
}