package com.example.moviesbymoviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesbymoviedb.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * from movie ORDER BY name LIMIT :limit")
    fun getAllMovies(limit: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(list: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * from movie WHERE id = :id")
    suspend fun selectMovieById(id: Int): Movie

    @Query("SELECT * FROM movie where is_favorite = 1 ")
    fun selectFavoriteMovies(): Flow<List<Movie>>

}