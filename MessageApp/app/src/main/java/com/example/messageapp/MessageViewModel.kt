package com.example.messageapp

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MessageViewModel (application: Application):AndroidViewModel(application){
    var messageList = MutableLiveData<ArrayList<Message>>()
    var userList = MutableLiveData<ArrayList<User>>()
    var currentUser = MutableLiveData<User>()
    private var database = MutableLiveData<DatabaseReference>()

    init {
        messageList.value = ArrayList()
        userList.value = ArrayList()

        database.value = FirebaseDatabase.getInstance().reference

//        val options = FirebaseOptions.Builder()
//            .setApplicationId("com.example.csi210.messageapp")
//            .setApiKey("AIzaSyClMCei-lMUK8V6XXL9mwb8XJkVV29wwmY")
//            .setDatabaseUrl("https://messageapp-project.firebaseio.com")
//            .build()

        //val app = FirebaseApp.initializeApp(application,options,"messageapp-project")
        //database.value = FirebaseDatabase.getInstance(app).reference

        database.value?.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                messageList.value!!.clear()
                userList.value!!.clear()
                p0.child("messages").children.forEach {
                    it.getValue(Message::class.java)?.let {
                        messageList.value?.add(it)
                    }
                }

                listener?.updateRecycler()
            }

        })
    }

    lateinit var rand: Random

    fun registerUser(userName: String){
        rand = Random(Calendar.getInstance().timeInMillis)
        val userColor = Color.rgb(
            rand.nextInt(255),
            rand.nextInt(255),
            rand.nextInt(255)
        )
        val user  = User(name = userName, color = userColor)
        database.value?.child("users")?.child(user.name)?.setValue(user)
        currentUser.value = user
    }

    fun sendMessage(text:String){
        val time = Calendar.getInstance().time.toString()
        database.value
            ?.child("messages")
            ?.child(time)
            ?.setValue(
                Message(
                    text = text,
                    timestamp = time,
                    user = currentUser.value!!.name
                )
        )

    }

    var listener: DataChangedListener? = null

    interface DataChangedListener{
        fun updateRecycler()
    }
}