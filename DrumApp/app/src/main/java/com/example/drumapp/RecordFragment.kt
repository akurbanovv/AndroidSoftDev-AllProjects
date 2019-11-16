package com.example.drumapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import kotlinx.android.synthetic.main.fragment_drum.*
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.item_saved.*
import kotlinx.android.synthetic.main.item_saved.view.*

/**
 * A simple [Fragment] subclass.
 */
class RecordFragment : Fragment(), DrumViewModel.DataChangedListener{
    override fun updateRecycler() {
        viewAdapter.notifyDataSetChanged()
    }

    lateinit var viewModel: DrumViewModel
    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.run{
            ViewModelProviders.of(this).get(DrumViewModel::class.java)
        }?: throw Exception("activity invalid")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.listener = this

        viewManager = LinearLayoutManager(context)
        viewAdapter = RecyclerViewAdapter(ArrayList()){
            itemSelected(it)
        }


        saved_recyclerview.apply {
            this.layoutManager = viewManager
            this.adapter = viewAdapter
        }

        viewModel.records.observe(this, Observer {
            viewAdapter.recordingList = it
            viewAdapter.notifyDataSetChanged()
        })



        ItemTouchHelper(SwipeHelper()).attachToRecyclerView(saved_recyclerview)
    }

    inner class SwipeHelper : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var curUser = viewModel.records.value!!.get(viewHolder.adapterPosition)

            if (viewModel.curRecName.value == curUser.name) {
                viewModel.deleteRecording(curUser)
            } else {
                Toast.makeText(getActivity(),"You cannot delete someone else's recording", Toast.LENGTH_LONG).show()
            }
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun itemSelected(recording: Recording){
        viewModel.replaySaved(recording)
    }

    class RecyclerViewAdapter(var recordingList: ArrayList<Recording>, val clickListener: (Recording) -> Unit) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_saved, parent, false)

            return RecyclerViewHolder(view)
        }

        override fun getItemCount(): Int {
            return recordingList.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(recordingList[position], clickListener)
        }

        class RecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(recording: Recording, clickListener: (Recording) -> Unit) {
                view.run {
                    view.findViewById<TextView>(R.id.name_text).text = recording.name
                    view.findViewById<TextView>(R.id.date_text).text = recording.date
                    view.findViewById<ImageButton>(R.id.play_button)
                        .setOnClickListener{clickListener(recording)}
                }
            }
        }
    }

}
