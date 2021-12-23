package com.ndzindo.tmdb.ui.movielist

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ndzindo.tmdb.R
import com.ndzindo.tmdb.data.CustomMovie
import com.ndzindo.tmdb.databinding.ItemMovieBinding

class CustomMovieAdapter(private val listener: OnCustomMovieClickInterface) :
    RecyclerView.Adapter<CustomMovieAdapter.MovieViewHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<CustomMovie>() {
        override fun areItemsTheSame(oldItem: CustomMovie, newItem: CustomMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CustomMovie, newItem: CustomMovie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie: CustomMovie = differ.currentList[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getMovie(position: Int): CustomMovie {
        return differ.currentList[position]
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val movie = getMovie(position)
                    listener.onCustomMovieItemClick(movie)
                }
            }
        }

        fun bind(movie: CustomMovie) {
            binding.apply {
                Glide.with(itemView)
                    .load(Uri.parse(movie.image))
                    .error(R.drawable.ic_error)
                    .into(imageViewPhoto)

                textViewTitle.text = movie.title
                textViewRating.isVisible = false
            }
        }
    }

    interface OnCustomMovieClickInterface {
        fun onCustomMovieItemClick(movie: CustomMovie)
    }
}
