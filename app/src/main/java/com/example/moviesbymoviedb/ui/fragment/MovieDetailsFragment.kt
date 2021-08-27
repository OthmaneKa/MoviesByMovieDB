package com.example.moviesbymoviedb.ui.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.moviesbymoviedb.R
import com.example.moviesbymoviedb.model.Movie
import com.squareup.picasso.Picasso

class MovieDetailsFragment : Fragment() {
    private var movie: Movie? = null
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var popularityTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var movieImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializable("movie") as Movie?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        initView(view)
        //populateView()
        return view
    }

    private fun initView(view: View) {
        view.let {
            titleTextView = it.findViewById(R.id.movie_details_name_text_view)
            movieImageView = it.findViewById(R.id.movie_details_image_view)
            popularityTextView = it.findViewById(R.id.movie_details_popularity_text_view)
            ratingTextView = it.findViewById(R.id.movie_details_count_text_view)
            dateTextView = it.findViewById(R.id.movie_details_date)
            contentTextView = it.findViewById(R.id.movie_details_content_text_view)
            contentTextView.movementMethod = ScrollingMovementMethod()
        }
        movie?.let {
            titleTextView.text = "Name : " + it.name
            val url = "https://image.tmdb.org/t/p/w500/" + it.backdropPath
            Picasso.get()
                .load(url)
                .into(movieImageView)
            popularityTextView.text = "Popularity : " + it.popularity.toString()
            ratingTextView.text = "Rating : " + it.voteAverage.toString()
            dateTextView.text = "Release Date : " + it.firstAirDate
            contentTextView.text = it.overview
        }
    }

    private fun populateView() {
        movie?.let {
            titleTextView.text = "Name : " + it.name
            val url = "https://image.tmdb.org/t/p/w500/" + it.backdropPath
            Picasso.get()
                .load(url)
                .centerCrop()
                .into(movieImageView)
            popularityTextView.text = it.popularity.toString()
            ratingTextView.text = it.voteAverage.toString()
            dateTextView.text = it.firstAirDate
            contentTextView.text = it.overview
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}