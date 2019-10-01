package com.example.nystreetfood


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), RatingBar.OnRatingBarChangeListener {
    var restaurantList = ArrayList<Restaurant>()
    var uid = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        restaurantList = listener.getRestaurantObject()
        uid = arguments!!.getInt("uid")

        if (uid == -1){
            uid = (0 until restaurantList.size).random()
        }

        subind_image.setImageResource(restaurantList[uid].imageBanner)
        ratingBar.rating = restaurantList[uid].rating.toFloat()
        name_text.text = restaurantList[uid].name
        subind_text.text = restaurantList[uid].kind
        subsubind_text.text = restaurantList[uid].type
        address_text.text = restaurantList[uid].address + ", " + restaurantList[uid].zip
        website_text.text = restaurantList[uid].website
        phone_text.text = restaurantList[uid].phone

        ratingBar.onRatingBarChangeListener = this

        phone_text.setOnClickListener {
            if (phone_text.text != "") {
                val callNumber = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone_text.text))
                startActivity(callNumber)
            }
        }
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        listener.updateRestaurantObject(uid, rating.toInt())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    lateinit var listener: OnFragmentInteractionListener

    interface OnFragmentInteractionListener {
        fun getRestaurantObject(): ArrayList<Restaurant>
        fun updateRestaurantObject(uid: Int, rating: Int)
    }

}
