package com.example.drumapp

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModelProviders
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class DrumWork(context: Context, parameter: WorkerParameters) : Worker(context, parameter) {

    var bassSound: MediaPlayer = MediaPlayer.create(context, R.raw.bass)
    var crashSound: MediaPlayer = MediaPlayer.create(context, R.raw.crash)
    var snareSound: MediaPlayer = MediaPlayer.create(context, R.raw.snare)

    override fun doWork(): Result {
        val sound = inputData.getInt("sound", -1)
        val mode = inputData.getString("mode")
        val sequence = inputData.getIntArray("sequence")
        val timestamps = inputData.getLongArray("timestamps")
        val recordingStartTime = inputData.getLong("start time", 0)

//        if (mode == "play") {
//            when (sound) {
//                0 ->
//                1 ->
//                2 ->
//            }
//        }

        if (mode == "replay" && sequence!!.isNotEmpty()) {
            var delay = timestamps!![0] - recordingStartTime

            for (i in sequence!!.indices) {
                Thread.sleep(delay)
                when (sequence[i]) {
                    0 -> bassSound.start()
                    1 -> crashSound.start()
                    2 -> snareSound.start()
                }
                delay = timestamps[i+1] - timestamps[i]
            }
        }

        return Result.success()
    }
}