package com.example.moviedb1


import android.content.Context
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_review.*

/**
 * A simple [Fragment] subclass.
 */
class ReviewFragment : Fragment() {

    lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel = activity?.run{
            ViewModelProviders.of(this).get(MovieViewModel::class.java)
        }?: throw Exception("activity invalid")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save_button.setOnClickListener{
            movieViewModel.currMovie.value?.review = edit_text.text.toString()
            movieViewModel.currMovie.value!!.rbar = ratingBar.rating.toDouble()
            findNavController().navigate(R.id.action_review_to_detail)
        }

        cancel_button.setOnClickListener{
            movieViewModel.currMovie.value!!.fakerbar = ratingBar.rating.toDouble()
            findNavController().navigate(R.id.action_review_to_detail)
        }

        movieViewModel.currMovie.observe(this, Observer { ratingBar.rating = it.rbar.toFloat() })
        movieViewModel.review.observe(this, Observer { edit_text.setText(it) })
        movieViewModel.currMovie.observe(this, Observer { GetPosters.getInstance().loadURL(it.postPath, view.findViewById(R.id.imageView))})
    }
}
