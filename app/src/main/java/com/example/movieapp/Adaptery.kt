package com.example.movieapp

import android.content.Context
import android.graphics.Movie
import com.example.movieapp.MovieModelClass
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Adaptery.MyViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.movieapp.R
import com.bumptech.glide.Glide
import android.widget.TextView

class Adaptery(private val mContext: Context, private val mData: List<MovieModelClass>, val clickListener: OnClickListener) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View
        val inflater = LayoutInflater.from(mContext)
        v = inflater.inflate(R.layout.movie_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = mData[position].id
        holder.name.text = mData[position].name

        //using Glide library to display the image

        //full link to posters https://image.tmdb.org/t/p/w500
        Glide.with(mContext)
            .load("https://image.tmdb.org/t/p/w500" + mData[position].img)
            .into(holder.img)

        holder.bind(mData[position], clickListener)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView
        var name: TextView
        var img: ImageView
        var movieModelClass: MovieModelClass? = null

        init {
            id = itemView.findViewById(R.id.id_txt)
            name = itemView.findViewById(R.id.name_txt)
            img = itemView.findViewById(R.id.imageView)
        }

        fun bind(movieModelClass: MovieModelClass, clickListener: OnClickListener) {

            this.movieModelClass = movieModelClass

            id.text = movieModelClass.id
            name.text = movieModelClass.name

           itemView.setOnClickListener{ clickListener.onClick(movieModelClass)}
        }
    }
}

class OnClickListener(val clickListener: (movie_id: MovieModelClass) -> Unit) {
    fun onClick(string: MovieModelClass) = clickListener(string)

}