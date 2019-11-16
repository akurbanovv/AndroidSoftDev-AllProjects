package com.example.moviedb1

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.csi210.weatherapp.MovieDB
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class MovieViewModel(application: Application): AndroidViewModel(application){

    var nowPlaying = MutableLiveData<ArrayList<Movie>>()
    var upComing = MutableLiveData<ArrayList<Movie>>()
    var savedList = MutableLiveData<ArrayList<Movie>>()
    var currMovie = MutableLiveData<Movie>()
    var movie = MutableLiveData<Movie>()
    var screen = MutableLiveData<Int>()

    private val apiURL = "https://api.themoviedb.org/"
    private val apiKey = "4e048397ab10d76819d91003eb0a62a0"

    val database = MovieDB.getDBObject(getApplication<Application>().applicationContext)
    val jsonGenre = getApplication<Application>().resources.openRawResource(R.raw.genre).bufferedReader().use { it.readText() }

    init {
        nowPlaying.value = ArrayList()
        upComing.value = ArrayList()
        savedList.value = database?.movieDAO()?.getAll() as ArrayList<Movie>
        savedList.value?.sortBy { sorted(it) }
        screen.value = 0
    }

    interface MovieService{
        @GET("3/movie/now_playing?")
        fun getNowPlaying(
            @Query("api_key") appid:String
        ): Call<ResponseBody>

        @GET("3/movie/upcoming?")
        fun getUpcoming(
            @Query("api_key") appid:String
        ): Call<ResponseBody>
    }

    fun fetchMovies(listName: String){
        val retrofit = Retrofit.Builder().baseUrl(apiURL).build()
        val service = retrofit.create(MovieService::class.java)

        if (listName == "Now Playing"){
            val call = service.getNowPlaying(apiKey)
            call.enqueue(MovieCallback(listName))
        }

        if (listName == "Up Coming"){
            val call = service.getUpcoming(apiKey)
            call.enqueue(MovieCallback(listName))
        }
    }

    fun addMovie(movie: Movie, screen: String,genre: HashMap<Int, String>){
        if (screen == "Now Playing"){
            nowPlaying.value?.add(movie)
        } else {
            upComing.value?.add(movie)
        }
    }

    fun getGenres(jarray: JSONArray,genre: HashMap<Int, String>): String{
        var genreName = ""
        for(i in 0 until  jarray.length()){
            genreName += genre[jarray[i]]
            genreName += " "
        }
        return genreName
    }


    fun removeMovieList(position:Int){
        savedList.value?.removeAt(position)
        updateList()
    }

    fun removeMovieDB(id:Int){
        database?.movieDAO()?.deleteMovieById(id)
    }

    fun sorted(movie: Movie): Double = movie.rbar

    fun saveMovie(movie: Movie){
        val savedMovie = savedList.value?.find { it.id == movie.id }

        if (savedMovie != null){
            database?.movieDAO()?.deleteMovieById(movie.id)
        }

        database?.movieDAO()?.insert(movie)
        updateList()
    }

    fun updateList(){
        savedList.value = database?.movieDAO()?.getAll() as ArrayList<Movie>
        savedList.value?.sortBy { sorted(it) }
    }

    fun parse(json: String, screen: String, genre: HashMap<Int, String>){

        val data = JSONObject(json)
        val dataArray = data.getJSONArray("results")

        upComing.value?.clear()
        nowPlaying.value?.clear()

        for (i in 0 until dataArray.length()) {
            val movieDetail = dataArray.getJSONObject(i)
            val movie = Movie("", "", 0.0, "", 0.0, "", "", "", 0)

            movie.name = movieDetail.getString("title")
            movie.date = movieDetail.getString("release_date")
            movie.rating = movieDetail.getDouble("vote_average")
            movie.overview = movieDetail.getString("overview")
            movie.postPath = movieDetail.getString("poster_path")
            movie.genre = getGenres(movieDetail.getJSONArray("genre_ids"), genre)
            movie.id = movieDetail.getInt("id")

            // if part

            addMovie(movie, screen, genre)
        }
    }

    fun manageReturn(json: String, screen: String){
        parse(json, screen, Parser(jsonGenre).codes)
    }

    inner class MovieCallback(listName: String): Callback<ResponseBody> {
        var screen = listName

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        }

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful){
                response.body()?.let {
                    manageReturn(it.string(), screen)
                    listener?.update()
                }
            }
        }

    }

    var listener: OnDataChangedListener? = null

    interface OnDataChangedListener {
        fun update()
    }
}