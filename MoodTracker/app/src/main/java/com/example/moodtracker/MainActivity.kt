package com.example.moodtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {}

    var curMoods = ""
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        curMoods = when(position) {
            0 -> "You were excited\uD83D\uDE06"
            1 -> "You were happy\uD83D\uDE01"
            2 -> "You were neutral\uD83D\uDE36"
            3 -> "You were sad\uD83D\uDE1E"
            4 -> "You were angry\uD83D\uDE21"

            else -> ""
        }
    }

    class Mood {
        var mood = ""
        var date = ""
    }

    var moodList: ArrayList<Mood>? = null

    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moodList = ArrayList()

        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.moodItems,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(moodList!!)
        mood_list.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }
        add_button.setOnClickListener {
            var mood = Mood()
            mood.mood = curMoods
            mood.date = Calendar.getInstance().time.toString()
            moodList?.add(mood)
            viewAdapter.notifyDataSetChanged()
        }
    }

    class RecyclerViewAdapter(val moodList: ArrayList<Mood>) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context).inflate(R.layout.emotion_item, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return moodList.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(moodList[position])
        }

        class RecyclerViewHolder(val viewItem: View) :
            RecyclerView.ViewHolder(viewItem) {
            fun bind(mood: Mood) {
                viewItem.findViewById<TextView>(R.id.time_text).text = mood.date
                viewItem.findViewById<TextView>(R.id.info_text).text = mood.mood
            }
        }
    }
}
