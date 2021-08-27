package com.example.moviesbymoviedb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    var firstAirDate: String = "",
    var overview: String = "",
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String = "",
    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String = "",
    @ColumnInfo(name = "origin_country")
    @SerializedName("origin_country")
    var originCountry: List<String>,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
    @ColumnInfo(name = "original_name")
    @SerializedName("original_name")
    var originalName: String = "",
    var popularity: Double = 0.0,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    var name: String = "",
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Int = 0,
    @Transient
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
) : Serializable
