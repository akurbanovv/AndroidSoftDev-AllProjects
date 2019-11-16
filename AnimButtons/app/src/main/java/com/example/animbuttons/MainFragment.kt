package com.example.animbuttons


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    lateinit var fly_down: AnimatorSet
    lateinit var fly_up: AnimatorSet
    lateinit var fly_left: AnimatorSet
    lateinit var fly_right: AnimatorSet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // red.setOnClickListener {

        //}

        red.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_redFragment)
        }

        blue.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_blueFragment)
        }

        yellow.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_yellowFragment)
        }

        green.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_greenFragment)
        }

        animateButtons()
    }


    fun animateButtons(){

        fly_down = AnimatorInflater.loadAnimator(context, R.animator.fly_down) as AnimatorSet
        fly_up = AnimatorInflater.loadAnimator(context, R.animator.fly_up) as AnimatorSet
        fly_left = AnimatorInflater.loadAnimator(context, R.animator.fly_left) as AnimatorSet
        fly_right = AnimatorInflater.loadAnimator(context, R.animator.fly_right) as AnimatorSet

        fly_right.apply {
            setTarget(red)
            start()
        }

        fly_down.apply {
            setTarget(green)
            start()
        }

        fly_left.apply {
            setTarget(blue)
            start()
        }

        fly_up.apply {
            setTarget(yellow)
            start()
        }




    }


    }
