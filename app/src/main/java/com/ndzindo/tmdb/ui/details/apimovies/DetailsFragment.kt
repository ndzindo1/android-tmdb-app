package com.ndzindo.tmdb.ui.details.apimovies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ndzindo.tmdb.R
import com.ndzindo.tmdb.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private val detailsViewModel by viewModels<DetailsViewModel>()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieDetailsBinding.bind(view)
        val movie = args.movie
        var isMovieFavourite = false

        binding.apply {

            Glide.with(this@DetailsFragment)
                .load(movie.poster_url)
                .error(R.drawable.ic_error)
                .fitCenter()
                .into(imageViewPoster)

            textViewTitle.text = movie.title
            textViewDescription.text = movie.overview
            textViewRating.text = movie.vote_average.toString()
            textViewVoteCount.text = "(${movie.vote_count})"
            buttonFavourite.setOnClickListener {
                if (!isMovieFavourite) {
                    detailsViewModel.addMovieToFavourite(movie)
                    showToast("Movie added to favorites.")
                } else {
                    detailsViewModel.removeMovieFromFavourites(movie)
                    showToast("Movie removed from favorites.")
                }
            }
        }
        detailsViewModel.getMovieEntry(movie.id)

        detailsViewModel.movieEntry.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.buttonFavourite.setImageResource(R.drawable.ic_not_favorite)
                isMovieFavourite = false
            } else {
                binding.buttonFavourite.setImageResource(R.drawable.ic_favorite)
                isMovieFavourite = true
            }
        }


    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}