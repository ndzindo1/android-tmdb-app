package com.ndzindo.tmdb.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ndzindo.tmdb.R
import com.ndzindo.tmdb.data.Movie
import com.ndzindo.tmdb.databinding.ItemMovieBinding

class FavoriteMovieAdapter(private val listener: OnClickListenerInterface) :
    RecyclerView.Adapter<FavoriteMovieAdapter.MovieViewHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie: Movie = differ.currentList[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getMovie(position: Int): Movie {
        return differ.currentList[position]
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val movie = getMovie(position)
                    listener.OnItemClick(movie)
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

    interface OnClickListenerInterface {
        fun OnItemClick(movie: Movie)
    }
}
