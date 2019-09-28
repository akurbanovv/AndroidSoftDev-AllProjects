package com.example.pcinventorymanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    class Computer {
        var name = ""
        var color = ""
    }

    var computerList: ArrayList<Computer>? = null
    var blackChecked = false
    var whiteChecked = false
    var sortedList = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        computerList = ArrayList()
        addButton.setOnClickListener {
            addItem(pcName.text.toString(), onRadioButtonClicked(radioGroup))
            showItem()
        }

        blackCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            blackChecked = b
            showItem()
        }

        whiteCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            whiteChecked = b
            showItem()
        }

        sortSwitch.setOnCheckedChangeListener { compoundButton, b ->
            sortedList = b
            showItem()
        }

    }

    fun onRadioButtonClicked(view:RadioGroup): String {
        when (view.checkedRadioButtonId) {
            blackButton.id ->
                return "Black"
            whiteButton.id ->
                return "White"
        }
        return ""
    }

    fun addItem(name: String, color: String) {
        val computer = Computer()
        computer.name = name
        computer.color = color
        computerList?.add(computer)
    }

    fun showItem() {
        var information = ""
        if (!blackChecked && !whiteChecked) {
            computerList?.forEach {
                information += "Name:${it.name}, Color:${it.color}\n"
            }
        }
        else {
            var filteredList = computerList?.filter {
                blackChecked && it.color == "Black" || whiteChecked && it.color == "White"
            }
            if (sortedList) {
                var sortedList = filteredList?.sortedBy { it.name }
                sortedList?.forEach {
                    information += "Name:${it.name}, Color:${it.color}\n"
                }
            } else {
                filteredList?.forEach {
                    information += "Name:${it.name}, Color:${it.color}\n"
                }
            }
        }
        textOutput.text = information
    }
}
