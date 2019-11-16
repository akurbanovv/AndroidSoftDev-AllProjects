package com.example.drumapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.firebase.database.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.database.DataSnapshot
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.security.Key
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.concurrent.CountDownLatch


class DrumViewModel(application: Application) : AndroidViewModel(application) {
    var sequence = MutableLiveData<ArrayList<Int>>()
    var timestamps = MutableLiveData<ArrayList<Long>>()
    var recordingStartTime = MutableLiveData<Date>()
    var database = MutableLiveData<DatabaseReference>()
    var records = MutableLiveData<ArrayList<Recording>>()
    var curRecName = MutableLiveData<String>()

    init {
        recordingStartTime.value = Calendar.getInstance().time
        sequence.value = ArrayList()
        timestamps.value = ArrayList()
        records.value = ArrayList()
        database.value = FirebaseDatabase.getInstance().reference
        curRecName.value = ""

        database.value?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                records.value!!.clear()

                p0.child("recordings").children.forEach { user ->
                    user.children.forEach { recordings ->
                        recordings.getValue(Recording::class.java)?.let { recording ->
                            records.value?.add(recording)
                        }
                    }
                }

                listener?.updateRecycler()
            }
        })
    }

    lateinit var drumWord: OneTimeWorkRequest
    val UNIQUE_WORK = "drum"

    fun addSound(type: Int) {
        when (type) {
            0 -> {
                sequence.value?.add(0)
                timestamps.value?.add(Calendar.getInstance().time.time)
            }
            1 -> {
                sequence.value?.add(1)
                timestamps.value?.add(Calendar.getInstance().time.time)
            }
            2 -> {
                sequence.value?.add(2)
                timestamps.value?.add(Calendar.getInstance().time.time)
            }
        }
    }

    fun addRecording(recording: Recording) {
        viewModelScope.launch {
            var finish = async { upload(recording) }
            finish.await()
        }
    }

    suspend fun upload(recording: Recording) = withContext(Dispatchers.IO) {
        database.value
            ?.child("recordings")
            ?.child(recording.name)
            ?.child(recording.date)
            ?.setValue(recording)

        println("uploaded")
    }


    fun deleteRecording(recording: Recording) {
        database.value
            ?.child("recordings")
            ?.child(recording.name)
            ?.child(recording.date)
            ?.setValue(null)
    }

    fun replaySaved(recording: Recording){
        val data = Data.Builder()
            .putString("mode", "replay")
            .putIntArray("sequence", recording.sequence!!.toIntArray())
            .putLongArray("timestamps", recording.timestamps!!.toLongArray())
            .putLong("start time", recording.dateCreated)
            .build()

        drumWord = OneTimeWorkRequest.Builder(DrumWork::class.java)
            .setInputData(data)
            .addTag(UNIQUE_WORK)
            .build()

        WorkManager.getInstance(getApplication())
            .enqueueUniqueWork(UNIQUE_WORK, ExistingWorkPolicy.KEEP, drumWord)
    }

    fun replay() {
        val data = Data.Builder()
            .putString("mode", "replay")
            .putIntArray("sequence", sequence.value!!.toIntArray())
            .putLongArray("timestamps", timestamps.value!!.toLongArray())
            .putLong("start time", recordingStartTime.value!!.time)
            .build()

        drumWord = OneTimeWorkRequest.Builder(DrumWork::class.java)
            .setInputData(data)
            .addTag(UNIQUE_WORK)
            .build()

        WorkManager.getInstance(getApplication())
            .enqueueUniqueWork(UNIQUE_WORK, ExistingWorkPolicy.KEEP, drumWord)
    }

    fun play(sound: Int) {
        val data = Data.Builder()
            .putString("mode", "play")
            .putInt("sound", sound)
            .build()

        drumWord = OneTimeWorkRequest.Builder(DrumWork::class.java)
            .setInputData(data)
            .addTag(UNIQUE_WORK)
            .build()

        WorkManager.getInstance(getApplication())
            .enqueueUniqueWork(UNIQUE_WORK, ExistingWorkPolicy.KEEP, drumWord)
    }

    var listener: DataChangedListener? = null

    interface DataChangedListener {
        fun updateRecycler()
    }
}