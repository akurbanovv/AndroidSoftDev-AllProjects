package com.example.moviedb1

import org.json.JSONObject

class Parser(json: String) {

    val codes = HashMap<Int, String>()

    //Parse the json file to a genre hashmap
    init {
        if (codes.size == 0) {
            val data = JSONObject(json)
            val dataArray = data.getJSONArray("genres")
            val result = ArrayList<Parser>()
            for (i in 0 until dataArray.length()) {
                val dataObject = dataArray.getJSONObject(i)
                codes[dataObject.getInt("id")] = dataObject.getString("name")

            }
        }
    }

}