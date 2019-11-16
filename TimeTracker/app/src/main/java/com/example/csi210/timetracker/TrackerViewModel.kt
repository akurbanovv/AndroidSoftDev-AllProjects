package com.example.csi210.timetracker

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class TrackerViewModel(application: Application) : AndroidViewModel(application) {

    val timeArray = MutableLiveData<ArrayList<Long>>()//Timestamps of recorded time
    var startTime = MutableLiveData<Date>()//The time when the app is launched
    private var database = MutableLiveData<DatabaseReference>()


    init {
        timeArray.value = ArrayList()
        startTime.value = Calendar.getInstance().time

        database.value = FirebaseDatabase.getInstance().reference
        database.value?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                timeArray.value!!.clear()
                p0.child("timeEntries").children.forEach {
                    it.value?.let { time ->
                        timeArray.value?.add(time as Long)
                    }
                }

                listener?.updateRecylcer()
            }

        })

    }

    fun trackTime() {
        val now = Calendar.getInstance().time
        val time = now.time - startTime.value?.time!!

        database.value?.child("timeEntries")?.child(time.toString())?.setValue(
            time
        )
    }

    fun resetStartTime() {
        startTime.value = Calendar.getInstance().time
    }

    fun resetRecords() {
        timeArray.value?.forEach {
            database.value?.child("timeEntries")?.child(it.toString())?.setValue(null)
        }
    }

    var listener: DataChangedListener? = null

    interface DataChangedListener {
        fun updateRecylcer()
    }

}