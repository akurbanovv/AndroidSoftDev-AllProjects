package com.example.moviedb1

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray
import org.json.JSONObject

class MovieViewModel: ViewModel() {

    var nowPlaying = MutableLiveData<ArrayList<Movie>>()
    var upComing = MutableLiveData<ArrayList<Movie>>()
    var savedList = MutableLiveData<ArrayList<Movie>>()
    var currMovie = MutableLiveData<Movie>()
    var review = MutableLiveData<String>()
    var isParsed = MutableLiveData<Boolean>()

    init {
        nowPlaying.value = ArrayList()
        upComing.value = ArrayList()
        savedList.value = ArrayList()
        review.value = "Add our review!"
        isParsed.value = false
    }

    fun addMovie(movie: Movie, screen: String,genre: HashMap<Int, String>){

        if (screen == "Now Playing"){
            movie.genre = getGenreName(movie.genreArr,genre)
            nowPlaying.value?.add(movie)
        }else{
            movie.genre = getGenreName(movie.genreArr,genre)
            upComing.value?.add(movie)
        }
    }

    fun getGenreName(jarray: JSONArray,genre: HashMap<Int, String>): String{
        var genreName = ""
        for(i in 0 until  jarray.length()){
            genreName += genre[jarray[i]]
            genreName += " "
        }
        return genreName
    }

    fun removeReview(index: Int){
        savedList.value?.removeAt(index) }

    fun removeMovie(position:Int){
        savedList.value?.removeAt(position)
    }

    fun sorted(movie: Movie):
            Double = movie.rbar

    fun saveMovie(movie: Movie){
        savedList.value?.add(movie)
        savedList.value?.sortBy { sorted(it) }
    }

    fun parse(json: String, screen: String, genre: HashMap<Int, String>){
        val data = JSONObject(json)
        val dataArray = data.getJSONArray("results")

        for (i in 0 until dataArray.length()) {
            val movieDetail = dataArray.getJSONObject(i)
            val movie = Movie("", "", 0.0, "", 0.0, "", "", JSONArray(), "", 0.0)
            movie.name = movieDetail.getString("title")
            movie.date = movieDetail.getString("release_date")
            movie.rating = movieDetail.getDouble("vote_average")
            movie.overview = movieDetail.getString("overview")
            movie.postPath = movieDetail.getString("poster_path")
            movie.genreArr = movieDetail.getJSONArray("genre_ids")
            addMovie(movie, screen, genre)
        }
    }





}