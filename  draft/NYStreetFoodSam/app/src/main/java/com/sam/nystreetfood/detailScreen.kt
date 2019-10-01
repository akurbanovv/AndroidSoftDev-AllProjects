package com.sam.nystreetfood


import android.Manifest.permission.CALL_PHONE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_detail_screen.*

/**
 * A simple [Fragment] subclass.
 */
class detailScreen : Fragment(), RatingBar.OnRatingBarChangeListener {
    var restaurantList = ArrayList<restaurantEntry>()
    var uid = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        restaurantList = listener.getRestaurantObject()
        uid = arguments!!.getInt("uid")

        if (uid == -1){
            uid = (0 until restaurantList.size).random()
        }

        typeImg.setImageResource(restaurantList[uid].imageBanner)
        rating.rating = restaurantList[uid].rating.toFloat()
        name.text = restaurantList[uid].name
        kind.text = restaurantList[uid].kind
        type.text = restaurantList[uid].type
        address.text = restaurantList[uid].address + ", " + restaurantList[uid].zip
        var website = restaurantList[uid].website
        callPhone.text = restaurantList[uid].phone

        rating.onRatingBarChangeListener = this

        visitWebsite.setOnClickListener {
            if (website == "") {
                Toast.makeText(context, "CSV doesn't contain website!", Toast.LENGTH_SHORT).show()
            } else {
                val openURL = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                startActivity(openURL)
            }
        }

        callPhone.setOnClickListener {
            if (callPhone.text == "") {
                Toast.makeText(context, "CSV doesn't contain phone number!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val callNumber = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + callPhone.text))
//            if (ContextCompat.checkSelfPermission(
//                    context!!,
//                    CALL_PHONE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                startActivity(callNumber)
//            } else {
//                requestPermissions(arrayOf(CALL_PHONE), 1)
//            }
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
        fun getRestaurantObject(): ArrayList<restaurantEntry>
        fun updateRestaurantObject(uid: Int, rating: Int)
    }

}
