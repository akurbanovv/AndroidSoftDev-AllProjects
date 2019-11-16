package com.example.drumapp


import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_drum.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DrumFragment : Fragment() {
    lateinit var viewModel: DrumViewModel
    var recording = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(DrumViewModel::class.java)
        return inflater.inflate(R.layout.fragment_drum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(DrumViewModel::class.java)

        var bassSound: MediaPlayer = MediaPlayer.create(context, R.raw.bass)
        var crashSound: MediaPlayer = MediaPlayer.create(context, R.raw.crash)
        var snareSound: MediaPlayer = MediaPlayer.create(context, R.raw.snare)

        bass_button.setOnClickListener {
            if (recording) viewModel.addSound(0)
            if (!bassSound.isPlaying ) {
//                if (recording) viewModel.addSound(0)
                bassSound.start()
            } else {

//                bassSound.stop()
//                bassSound.release()
//                bassSound = MediaPlayer.create(context, R.raw.bass)
                crashSound.seekTo(0)
                bassSound.start()
                if (recording) viewModel.addSound(0)
            }
        }

        crash_button.setOnClickListener {
            if (recording) viewModel.addSound(1)
            if (!crashSound.isPlaying) {
//                if (recording) viewModel.addSound(1)
                crashSound.start()
            } else {
//                crashSound.stop()
//                crashSound.release()
//                crashSound = MediaPlayer.create(context, R.raw.crash)
                crashSound.seekTo(0)
                crashSound.start()
                if (recording) viewModel.addSound(1)

            }
        }

        snare_button.setOnClickListener {
            if (recording) viewModel.addSound(2)
            if (!snareSound.isPlaying) {
//                if (recording) viewModel.addSound(2)
                snareSound.start()
            } else {
//                snareSound.stop()
//                snareSound.release()
//                snareSound = MediaPlayer.create(context, R.raw.snare)
                crashSound.seekTo(0)
                snareSound.start()
                if (recording) viewModel.addSound(2)
            }
        }

        replay_button.setOnClickListener {
            viewModel.replay()
        }

        record_button.setOnClickListener {
            when (recording) {
                true -> {
                    recording = false
                    record_button.setImageResource(R.drawable.ic_fiber_manual_record_black_24dp)
                }
                false -> {
                    viewModel.sequence.value?.clear()
                    viewModel.timestamps.value?.clear()

                    recording = true
                    viewModel.recordingStartTime.value = Calendar.getInstance().time

                    record_button.setImageResource(R.drawable.ic_stop_black_24dp)
                }
            }
        }

        save_button.setOnClickListener {
            var name = arguments?.getString("name").toString()
            var date = Calendar.getInstance().time
            var id = name.hashCode().toString()

            viewModel.curRecName.value = name

            viewModel.addRecording(
                Recording(
                    id,
                    name,
                    date.toString(),
                    viewModel.recordingStartTime.value!!.time,
                    viewModel.sequence.value!!.toList(),
                    viewModel.timestamps.value!!.toList()
                )
            )

            Toast.makeText(getActivity(), "Your record is saved!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_drumFragment_to_recordFragment)

        }
    }


}
