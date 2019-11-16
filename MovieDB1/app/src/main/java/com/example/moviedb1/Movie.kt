package com.example.moviedb1

import org.json.JSONArray
import org.json.JSONObject

class Movie(
    var name: String,
    var date: String,
    var rating: Double,
    var overview: String,
    var rbar: Double,
    var postPath: String,
    var genre: String,
    var genreArr: JSONArray,
    var review: String,
    var fakerbar: Double
)
