package com.example.moviesbymoviedb.model

data class MoviesList(
    val page: Int,
    val totalPages: Int,
    val results: ArrayList<Movie>,
    val totalResults: Int?
)

