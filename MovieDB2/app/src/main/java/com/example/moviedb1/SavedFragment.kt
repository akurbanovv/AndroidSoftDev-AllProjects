package com.example.moviedb1


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_saved.*

/**
 * A simple [Fragment] subclass.
 */
class SavedFragment : Fragment() {

    lateinit var movieViewModel: MovieViewModel
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movieViewModel = activity?.run{
            ViewModelProviders.of(this).get(MovieViewModel::class.java)
        }?:throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.savedList.observe(this, Observer {
            viewManager = LinearLayoutManager(context)
            viewAdapter = RecyclerViewAdapter(movieViewModel.savedList.value!!) {
                movieViewModel.currMovie.value = it
                findNavController().navigate(R.id.action_saved_to_detail)
            }
            saved_recyclerview.apply {
                this.layoutManager = viewManager
                this.adapter = viewAdapter
            }
        })

        ItemTouchHelper(SwipeHelper()).attachToRecyclerView(saved_recyclerview)
    }

    inner class SwipeHelper: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            movieViewModel.removeMovieDB(
                    movieViewModel.savedList.value!!.get(viewHolder.adapterPosition).id
            )
            movieViewModel.removeMovieList(viewHolder.adapterPosition)
            viewAdapter.notifyDataSetChanged()
        }
    }

    class RecyclerViewAdapter(val list: ArrayList<Movie>, val clickListener: (Movie) -> Unit):
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_saved, parent, false)
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

                GetPosters.getInstance().loadURL(movie.postPath, viewMovie.findViewById(R.id.poster_saveView))
                viewMovie.findViewById<RatingBar>(R.id.savedRatingBar).rating = movie.rbar.toFloat()
                viewMovie.findViewById<TextView>(R.id.name_save).text = movie.name
                viewMovie.setOnClickListener{clickListener(movie)}
            }
        }
    }


}
