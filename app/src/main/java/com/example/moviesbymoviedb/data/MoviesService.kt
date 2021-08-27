package com.example.moviesbymoviedb.data

import com.example.moviesbymoviedb.model.Movie
import com.example.moviesbymoviedb.model.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("popular")
    suspend fun getMoviesByPage(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesList>
}