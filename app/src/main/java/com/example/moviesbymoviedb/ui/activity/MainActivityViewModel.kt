package com.example.moviesbymoviedb.ui.activity

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesbymoviedb.data.MoviesApi
import com.example.moviesbymoviedb.model.Movie
import com.example.moviesbymoviedb.model.MoviesList
import kotlinx.coroutines.*

class MainActivityViewModel : ViewModel() {

    val movieList = MutableLiveData<MoviesList>()


    private fun executeCall(): MutableLiveData<MoviesList> {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val apiKey = "8d6a13d4ff986513574e73180f498ed9"
                val langage = "en-US"
                val response = MoviesApi.moviesService.getMoviesByPage(apiKey, langage, 1)

                if (response.isSuccessful && response.body() != null) {
                    movieList.postValue(response.body())
                    println(movieList)
                } else {
                    Log.e("ERROR", "NOTHING")
                }

            } catch (e: Exception) {
                Log.e("ERROR", e.stackTrace.toString())
            }
        }
        return movieList
    }
}