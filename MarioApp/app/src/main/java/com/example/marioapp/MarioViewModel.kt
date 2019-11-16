package com.example.marioapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*


class MarioViewModel(application: Application): AndroidViewModel(application) {

    lateinit var countDown: OneTimeWorkRequest
    val UNIQUE_WORK = "counting"

    fun click(){
        countDown = OneTimeWorkRequest.Builder(Worker::class.java)
            .addTag(UNIQUE_WORK)
            .build()

        WorkManager.getInstance(getApplication())
            .enqueueUniqueWork(
                UNIQUE_WORK, ExistingWorkPolicy.KEEP, countDown
            )
    }

    fun getWorkInfo(): LiveData<WorkInfo> {
        return WorkManager.getInstance(getApplication()).getWorkInfoByIdLiveData(countDown.id)
    }


}