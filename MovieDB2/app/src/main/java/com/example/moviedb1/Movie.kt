package com.example.moviedb1

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "movieDB")

class Movie(
    var name: String,
    var date: String,
    var rating: Double,
    var overview: String,
    var rbar: Double,
    var postPath: String,
    var genre: String,
    var review: String,

    @PrimaryKey
    var id: Int
)
