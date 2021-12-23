package com.ndzindo.tmdb.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ndzindo.tmdb.R
import com.ndzindo.tmdb.data.Movie
import com.ndzindo.tmdb.databinding.ItemMovieBinding

class MovieAdapter(private val listener: OnItemClickInterface) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MOVIE_DIFF_CALLBACK) {


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = getItem(position)

        if (currentMovie != null) {
            holder.bind(currentMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val movie = getItem(position)
                    if (movie != null) {
                        listener.OnItemClick(movie)
                    }
                }
            }
        }

        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(itemView)
                    .load(movie.poster_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewPhoto)

                textViewTitle.text = movie.title

                textViewRating.text = movie.vote_average.toString()
            }
        }
    }

    interface OnItemClickInterface {
        fun OnItemClick(movie: Movie)
    }

    companion object {
        private val MOVIE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}