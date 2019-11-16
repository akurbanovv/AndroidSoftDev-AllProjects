package com.example.moviedb1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.net.URL


class GetPosters {

    companion object {
        private var loader: GetPosters? = null

        fun getInstance(): GetPosters {
            if (loader == null) {
                loader = GetPosters()
            }
            return loader!!
        }
    }

    val baseUrl = "http://image.tmdb.org/t/p/w185"

    fun loadURL(url: String, imgView: ImageView) {
        LoaderAsync(imgView).execute(baseUrl + url)
    }

    private class LoaderAsync(internal var bmImage: ImageView) :
        AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urldisplay = urls[0]
            println(urldisplay)
            var mIcon11: Bitmap? = null
            try {
                val url = URL(urldisplay)
                mIcon11 = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mIcon11
        }

        override fun onPostExecute(result: Bitmap) {
            bmImage.setImageBitmap(result)
        }
    }
}