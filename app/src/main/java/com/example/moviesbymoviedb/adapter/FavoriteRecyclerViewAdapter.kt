package com.example.moviesbymoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesbymoviedb.R
import com.example.moviesbymoviedb.model.Movie
import com.squareup.picasso.Picasso

class FavoriteRecyclerViewAdapter(private var mListener: MyClickListener) :
    RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {

    private var list: List<Movie> = ArrayList()

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val title = itemView.findViewById<TextView>(R.id.movie_title_text_view)
        private val image = itemView.findViewById<ImageView>(R.id.movie_image_view)
        private val rating = itemView.findViewById<TextView>(R.id.movie_rating_text_view)
        private val popularity = itemView.findViewById<TextView>(R.id.movie_count_text_view)
        private val favorite = itemView.findViewById<ImageView>(R.id.like_image_view)
        private val container = itemView.findViewById<CardView>(R.id.card_view)

        fun bindHolder(movie: Movie) {
            title.text = movie.name
            val url = "https://image.tmdb.org/t/p/w500/" + movie.backdropPath
            Picasso.get()
                .load(url)
                .resize(220, 160)
                .centerCrop()
                .into(image)
            rating.text = movie.voteAverage.toString()
            popularity.text = movie.voteCount.toString()

            if (movie.isFavorite) {
                itemView.findViewById<ImageView>(R.id.like_image_view)
                    .setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                itemView.findViewById<ImageView>(R.id.like_image_view)
                    .setImageResource(R.drawable.ic_not_liked)
            }
            container.setOnClickListener {
                mListener.onContainerClicked(adapterPosition)
            }

        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.card_view -> {
                    mListener.onContainerClicked(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<Movie>) {
        this.list = list
    }

    interface MyClickListener {
        fun onContainerClicked(position: Int)
    }


}