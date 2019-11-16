package com.example.marioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(MarioViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener{
            viewModel.click()
            observeCounter()
        }
    }

    fun observeCounter() {
        viewModel.getWorkInfo().observe( this, Observer {
            if (it != null) {
                when (it.state) {
                    WorkInfo.State.ENQUEUED ->{ }

                    WorkInfo.State.RUNNING -> {
                        it.progress.getInt("timeSec", 3).run {
                            timer_text.text = this.toString()
                        }
                    }
                    WorkInfo.State.SUCCEEDED -> {}
                }
            }
        })

    }
}
