package com.example.messageapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MessageViewModel.DataChangedListener {
    override fun updateRecycler() {
        viewAdapter.notifyDataSetChanged()
        message_recyclerView.smoothScrollToPosition(Int.MAX_VALUE)

    }

    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(ArrayList(), ArrayList())
        viewManager.reverseLayout = true //add things at the top instead of bottom
        viewManager.stackFromEnd = true

        message_recyclerView?.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        viewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        viewModel.listener = this

        viewModel.messageList.observe(this, Observer {
            viewAdapter.messageList = it
            viewAdapter.notifyDataSetChanged()
        })
        viewModel.userList.observe(this, Observer {
            viewAdapter.userList = it
            viewAdapter.notifyDataSetChanged()
        })

        login_button.setOnClickListener {
            if (name_text.text.isNotBlank()){
                viewModel.registerUser(name_text.text.toString())

                viewModel.currentUser.observe(this, Observer {
                    chat_bg.setBackgroundColor(it.color)
                })

            }else{
                Toast.makeText(this,"User name cannot be empty",Toast.LENGTH_LONG).show()
            }
        }

        send_button.setOnClickListener {
            if (viewModel.currentUser.value != null){
                viewModel.sendMessage(message_text.text.toString())
            }else{
                Toast.makeText(this,"Login First",Toast.LENGTH_LONG).show()
            }
        }
    }
    class RecyclerViewAdapter(var messageList: ArrayList<Message>, var userList: ArrayList<User>) :
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
            return RecyclerViewHolder(view)
        }

        override fun getItemCount(): Int {
            return messageList.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(messageList[position], userList)
        }

        class RecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            fun bind(message: Message, userList: ArrayList<User>) {
                view.findViewById<TextView>(R.id.msg_message_text).text = message.text
                view.findViewById<TextView>(R.id.msg_time_text).text = message.timestamp

                userList.find { it.name == message.user }?.let {
                    view.findViewById<TextView>(R.id.msg_name_text).text = it.name
                    view.findViewById<TextView>(R.id.msg_name_text).setTextColor(it.color)
                    view.findViewById<TextView>(R.id.msg_message_text).setTextColor(it.color)
                }
            }
        }
    }
}
