package com.example.movieapp

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class DetailsActivity : AppCompatActivity() {
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        poster = findViewById(R.id.poster)
        title = findViewById(R.id.title)
        releaseDate = findViewById(R.id.date)
        overview = findViewById(R.id.overview)

        var extras = intent.extras
        var id = extras!!.getString("movie_id")

        if(extras != null){
            populateDetails(extras)
        }else {
            finish()
        }



//            movieList = ArrayList()
//            val getData = GetData()
//            getData.execute()


        //find view by id


        //call do api z get details


        //do odpowiednich view odpowiednie rzeczy JASON
    }

    private fun populateDetails(extras: Bundle){
        extras.getString(MOVIE_POSTER)?.let { img ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + img)
                .transform(CenterCrop())
                .into(poster)
        }
        title.text = extras.getString(MOVIE_TITLE, "")
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW,"")
    }



}