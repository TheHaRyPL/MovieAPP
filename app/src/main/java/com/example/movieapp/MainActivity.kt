package com.example.movieapp

import android.content.Intent
import android.graphics.Movie
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    var movieList: MutableList<MovieModelClass>? = null
    var recyclerView: RecyclerView? = null
    var header: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieList = ArrayList()
        recyclerView = findViewById(R.id.recyclerView)
        header = findViewById(R.id.imageView3)
        header?.let {
            Glide.with(this)
                .load(R.drawable.img)
                .into(it)
        }
        val getData = GetData()
        getData.execute()
    }

    //details
    private fun showMovieDetails(movie: MovieModelClass) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_POSTER, movie.img)
        intent.putExtra(MOVIE_TITLE, movie.name)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.date)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

    inner class GetData : AsyncTask<String?, String?, String>() {
        override fun doInBackground(vararg strings: String?): String {
            var current = ""
            try {
                val url: URL
                var urlConnection: HttpURLConnection? = null
                try {
                    url = URL(JSON_URL)
                    urlConnection = url.openConnection() as HttpURLConnection
                    val `is` = urlConnection.inputStream
                    val isr = InputStreamReader(`is`)
                    var data = isr.read()
                    while (data != -1) {
                        current += data.toChar()
                        data = isr.read()
                    }
                    return current
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return current
        }

        override fun onPostExecute(s: String) {
            try {
                val jsonObject = JSONObject(s)
                val jsonArray = jsonObject.getJSONArray("results")
                for (i in 0 until jsonArray.length()) {
                    val jsonObject1 = jsonArray.getJSONObject(i)
                    val model = MovieModelClass()
                    model.movie_id = jsonObject1.getString("id")
                    model.id = jsonObject1.getString("vote_average")
                    model.name = jsonObject1.getString("title")
                    model.img = jsonObject1.getString("poster_path")
                    model.overview = jsonObject1.getString("overview")
                    model.date = jsonObject1.getString("release_date")
                    movieList!!.add(model)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            PutDataIntoRecyclerView(movieList)
        }

    }

    private fun PutDataIntoRecyclerView(movieList: List<MovieModelClass>?) {
        val adaptery = Adaptery(this, movieList!!, OnClickListener { movie ->
            var i = Intent(this, DetailsActivity::class.java)
            i.putExtra("movie_id", movie.movie_id)
            i.putExtra("release_date", movie.date)
            i.putExtra("overview", movie.overview)
            i.putExtra("poster_path", movie.img)
            i.putExtra("title", movie.name)
            startActivity(i)
        })
        recyclerView!!.layoutManager = GridLayoutManager(this, 3)
        recyclerView!!.adapter = adaptery


    }

    companion object {
        // Json link https://api.themoviedb.org/3/movie/popular?api_key=10463aec84d18c314cd746d6771eabd5
        private const val JSON_URL =
            "https://api.themoviedb.org/3/movie/popular?api_key=10463aec84d18c314cd746d6771eabd5"
    }
}