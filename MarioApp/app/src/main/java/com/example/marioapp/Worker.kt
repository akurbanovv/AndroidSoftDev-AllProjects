package com.example.marioapp

import android.content.Context
import android.media.MediaPlayer
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class Worker(context: Context, parameter: WorkerParameters) : Worker(context, parameter) {


    var marioSound: MediaPlayer = MediaPlayer.create(context, R.raw.mario)

    override fun doWork(): Result {
        for ( i in 3 downTo 0) {

            setProgressAsync((
                    Data.Builder()
                        .putInt("timeSec", i)).build())

            Thread.sleep(1000)
        }

        marioSound.start()

        return Result.success()
    }
}