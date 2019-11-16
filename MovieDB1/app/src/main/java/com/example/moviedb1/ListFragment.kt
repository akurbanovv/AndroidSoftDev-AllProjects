package com.example.moviedb1


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */


class ListFragment : Fragment() {

    lateinit var movieViewModel: MovieViewModel
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager
    var isLoadedNP = false
    var isLoadedUP = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel = activity?.run {
            ViewModelProviders.of(this).get(MovieViewModel::class.java)
        } ?: throw Exception("activity invalid")

        var type = ""
        if ((arguments?.getString("style")) != null) {
                type = arguments?.getString("style").toString()
            }

        val jsonGenre = resources.openRawResource(R.raw.genre).bufferedReader().use { it.readText() }

        when (type) {
            "Now Playing" ->
                if (!isLoadedNP){
                    isLoadedNP = true
                    movieViewModel.parse(resources.openRawResource(R.raw.now_playing).bufferedReader().use { it.readText() }, "Now Playing", Parser(jsonGenre).codes)}
            "Up Coming" ->
                if (!isLoadedUP){
                    isLoadedUP = true
                    (activity as AppCompatActivity).supportActionBar?.title = "Upcoming"
                    movieViewModel.parse(resources.openRawResource(R.raw.upcoming).bufferedReader().use { it.readText() }, "Up Coming", Parser(jsonGenre).codes)}
            "" ->
                if (!isLoadedNP){
                    isLoadedNP = true
                    movieViewModel.parse(resources.openRawResource(R.raw.now_playing).bufferedReader().use { it.readText() }, "Now Playing", Parser(jsonGenre).codes)
                }
        }

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.nowPlaying.observe(this, Observer {
            viewManager = LinearLayoutManager(context)
            viewAdapter = RecyclerViewAdapter(movieViewModel.nowPlaying.value!!) {

                movieViewModel.currMovie.value = it
                findNavController().navigate(R.id.action_nowPlaying_to_detail)
            }

            (activity as AppCompatActivity).supportActionBar?.title = "Now Playing"

            list_recyclerview.apply {
                this.layoutManager = viewManager
                this.adapter = viewAdapter
            }
        })

        var type = arguments?.getString("style")
        when (type) {
            "Now Playing" -> {
                movieViewModel.nowPlaying.observe(this, Observer {
                    viewManager = LinearLayoutManager(context)
                    viewAdapter = RecyclerViewAdapter(movieViewModel.nowPlaying.value!!) {

                        movieViewModel.currMovie.value = it
                        findNavController().navigate(R.id.action_nowPlaying_to_detail)
                    }

                    (activity as AppCompatActivity).supportActionBar?.title = "Now Playing"

                    list_recyclerview.apply {
                        this.layoutManager = viewManager
                        this.adapter = viewAdapter
                    }
                })
            }

            "Up Coming" -> {
                movieViewModel.upComing.observe(this, Observer {
                    viewManager = LinearLayoutManager(context)
                    viewAdapter = RecyclerViewAdapter(movieViewModel.upComing.value!!) {

                        movieViewModel.currMovie.value = it
                        findNavController().navigate(R.id.action_nowPlaying_to_detail)
                    }

                    (activity as AppCompatActivity).supportActionBar?.title = "Upcoming"

                    list_recyclerview.apply {
                        this.layoutManager = viewManager
                        this.adapter = viewAdapter
                    }
                })
            }
        }
    }


    class RecyclerViewAdapter(val list: ArrayList<Movie>, val clickListener: (Movie) -> Unit) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(list[position], clickListener)
        }

        class RecyclerViewHolder(val viewMovie: View) : RecyclerView.ViewHolder(viewMovie) {
            fun bind(movie: Movie, clickListener: (Movie) -> Unit) {

                GetPosters.getInstance()
                    .loadURL(movie.postPath, viewMovie.findViewById(R.id.poster_imageView))
                viewMovie.findViewById<TextView>(R.id.name_textview).text = movie.name
                viewMovie.findViewById<TextView>(R.id.genre_textview).text = movie.genre
                viewMovie.setOnClickListener { clickListener(movie) }
            }
        }
    }
}


