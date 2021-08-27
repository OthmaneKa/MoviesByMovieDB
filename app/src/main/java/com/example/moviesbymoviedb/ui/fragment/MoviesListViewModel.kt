package com.example.moviesbymoviedb.ui.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.moviesbymoviedb.data.LocalDb
import com.example.moviesbymoviedb.data.MovieDao
import com.example.moviesbymoviedb.data.MoviesApi
import com.example.moviesbymoviedb.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

private const val ITEM_PER_PAGE = 20
private const val MAX_FETCH = 20
private const val API_KEY = "8d6a13d4ff986513574e73180f498ed9"
private const val LANGUAGE = "en-US"

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {
    private var moviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    private var localList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    private var emptyList: ArrayList<Movie> = ArrayList()
    private var movieDao: MovieDao
    private var page = 1
    private var loadNextPage = true

    init {
        val localDb = LocalDb.getInstance(application.applicationContext)
        movieDao = localDb?.movieDao()!!
        fetchData()
        fetchFromLocal()
    }

    fun setOnlineObserver(): MutableLiveData<ArrayList<Movie>> {
        return moviesList
    }

    fun setLocalObserver(): MutableLiveData<ArrayList<Movie>> {
        return localList
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val call = MoviesApi.moviesService.getMoviesByPage(API_KEY, LANGUAGE, page)
                if (call.isSuccessful && call.body() != null) {
                    call.body()?.let {
                        val result = it.results
                        moviesList.postValue(result)
                        movieDao.insertAllMovies(result)
                        if (result.size < ITEM_PER_PAGE)
                            loadNextPage = false
                    }
                } else {
                    Log.e("ERROR", "NOTHING")
                }

            } catch (e: Exception) {
                Log.e("ERROR", e.stackTrace.toString())
            }
        }
    }

    fun updateDatabase(movie: Movie) {
        viewModelScope.launch {
            movieDao.insertMovie(movie)
        }
    }

    private fun fetchFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val limit = page * MAX_FETCH
                val result = movieDao.getAllMovies(limit)
                emptyList.clear()
                emptyList.addAll(result)
                localList.postValue(emptyList)
            } catch (e: Exception) {
                Log.e("ERROR LOCAL", e.message!!)
            }
        }
    }

    fun canLoadNextPage(): Boolean {
        return loadNextPage
    }

    fun setLoadNextPage(boolean: Boolean) {
        loadNextPage = boolean
    }

    fun loadNextPage(indicator: Boolean) {
        page++
        if (indicator)
            fetchData()
        else
            fetchFromLocal()
    }
}