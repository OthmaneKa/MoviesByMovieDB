package com.example.moviesbymoviedb.ui.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesbymoviedb.data.LocalDb
import com.example.moviesbymoviedb.data.MovieDao
import com.example.moviesbymoviedb.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var moviesList: LiveData<List<Movie>>
    private var movieDao: MovieDao

    init {
        val db = LocalDb.getInstance(application.applicationContext)
        movieDao = db?.movieDao()!!
        fetchData()
    }

    fun setObserver(): LiveData<List<Movie>> {
        return moviesList
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val result: Flow<List<Movie>> = movieDao.selectFavoriteMovies()
                moviesList = result.asLiveData()
            } catch (e: Exception) {
                Log.e("Error while fetching favorite films", e.stackTraceToString())
            }
        }
    }
}