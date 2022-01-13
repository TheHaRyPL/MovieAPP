package com.example.movieapp

class MovieModelClass {
    var movie_id: String? = null
    var id: String? = null
    var name: String? = null
    var img: String? = null
    var date: String? = null
    var overview: String? = null

    constructor(id: String?, name: String?, img: String?) {
        this.movie_id = movie_id
        this.id = id
        this.name = name
        this.img = img
        this.date = date
        this.overview = overview
    }

    constructor() {}
}