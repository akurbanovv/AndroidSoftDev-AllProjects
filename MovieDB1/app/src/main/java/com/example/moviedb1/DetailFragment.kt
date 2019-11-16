package com.example.moviedb1


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel = activity?.run{
            ViewModelProviders.of(this).get(MovieViewModel::class.java)
        }?: throw Exception("activitiy invlaid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.currMovie.observe(this, Observer { name_detail.text = it.name })
        movieViewModel.currMovie.observe(this, Observer { date_detail.text = it.date })
        movieViewModel.currMovie.observe(this, Observer { rating_detail.text = "Rating: " + it.rating.toString() })
        movieViewModel.currMovie.observe(this, Observer { overview_detail.text = it.overview })
        movieViewModel.currMovie.observe(this, Observer {
            if (it.rbar.toString() == "0" ){
                ratingBar_detail.rating = it.fakerbar.toFloat()}
            else {
                ratingBar_detail.rating = it.rbar.toFloat()
            }

        })
        // movieViewModel.currMovie.observe(this, Observer { ratingBar_detail.rating = it.rbar.toFloat() })
        movieViewModel.currMovie.observe(this, Observer { genre_detail.text = it.genre })
        movieViewModel.currMovie.observe(this, Observer { GetPosters.getInstance().loadURL(it.postPath, view.findViewById(R.id.imageView))})
        movieViewModel.currMovie.observe(this, Observer {review_detail.text = it.review})

        edit_button.setOnClickListener{
            findNavController().navigate(R.id.action_detail_to_review)
        }

        saved_button.setOnClickListener{
            movieViewModel.currMovie.observe(this, Observer { movieViewModel.saveMovie(it) })
            findNavController().navigate(R.id.action_detail_to_review)
        }
    }
}
