package com.example.csi210.timetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.milliseconds

class MainActivity : AppCompatActivity(), TrackerViewModel.DataChangedListener {

    override fun updateRecylcer() {
        viewAdapter.notifyDataSetChanged()
        recycler_view.smoothScrollToPosition(Int.MAX_VALUE)

    }

    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var viewModel: TrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(TrackerViewModel::class.java)

        viewModel.listener = this

        viewModel.timeArray.observe(this, androidx.lifecycle.Observer {
            viewManager = LinearLayoutManager(this)
            viewAdapter = RecyclerViewAdapter(it)

            recycler_view.apply {
                this.adapter = viewAdapter
                this.layoutManager = viewManager
            }
        })

        track_button.setOnClickListener {
            viewModel.trackTime()
            viewAdapter.notifyDataSetChanged()
        }

        clear_button.setOnClickListener {
            viewModel.resetRecords()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resetStartTime()
    }

    class RecyclerViewAdapter(var timeList: ArrayList<Long>) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.time_item, parent, false)
            return RecyclerViewHolder(view)
        }

        override fun getItemCount(): Int {
            return timeList.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(timeList[position])
        }

        class RecyclerViewHolder(val view: View) :
            RecyclerView.ViewHolder(view) {
            fun bind(timeStamp: Long) {
                view.findViewById<TextView>(R.id.time_text).text =
                    "app is running $timeStamp millis"
            }
        }
    }
}
