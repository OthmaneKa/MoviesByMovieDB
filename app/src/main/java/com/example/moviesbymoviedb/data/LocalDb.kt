package com.example.moviesbymoviedb.data

import android.content.Context
import androidx.room.*
import com.example.moviesbymoviedb.model.Movie
import com.example.moviesbymoviedb.utils.Converter

@Database(entities = [Movie::class], exportSchema = false, version = 1)
@TypeConverters(Converter::class)
abstract class LocalDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: LocalDb? = null

        fun getInstance(context: Context): LocalDb? {
            if (instance == null) {
                instance = Room.databaseBuilder(context, LocalDb::class.java, "movie_db").build()
            }
            return instance
        }

        fun destroyDb() {
            instance == null
        }
    }
}