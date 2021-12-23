package com.ndzindo.tmdb.ui.details.custommovies

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ndzindo.tmdb.R
import com.ndzindo.tmdb.databinding.FragmentCustomMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomMovieDetailsFragment : Fragment(R.layout.fragment_custom_movie_details) {

    private val args by navArgs<CustomMovieDetailsFragmentArgs>()
    private lateinit var binding: FragmentCustomMovieDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCustomMovieDetailsBinding.bind(view)
        val movie = args.movie

        binding.apply {

            Glide.with(requireContext())
                .load(Uri.parse(movie.image))
                .error(R.drawable.ic_error)
                .into(imageViewPoster)

            textViewTitle.text = movie.title
            textViewDescription.text = movie.description

        }

    }
}