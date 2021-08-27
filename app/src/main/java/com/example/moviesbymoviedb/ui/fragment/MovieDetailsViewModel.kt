package com.example.moviesbymoviedb.ui.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesbymoviedb.data.LocalDb
import com.example.moviesbymoviedb.data.MovieDao
import com.example.moviesbymoviedb.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var movie: Movie
    private var movieDao: MovieDao

    init {
        val localDb = LocalDb.getInstance(application.applicationContext)
        movieDao = localDb!!.movieDao()
    }

    fun getMovieByID(id: Int): Movie {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movie = movieDao.selectMovieById(id)
            } catch (e: Exception) {
                Log.e("ERROR WHILE QUERY (SELECT BY ID)", e.stackTraceToString())
            }
        }
        return movie
    }
}