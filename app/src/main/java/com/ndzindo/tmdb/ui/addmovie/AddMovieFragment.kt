package com.ndzindo.tmdb.ui.addmovie

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.ndzindo.tmdb.R
import com.ndzindo.tmdb.data.CustomMovie
import com.ndzindo.tmdb.databinding.FragmentAddMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileNotFoundException

@AndroidEntryPoint
class AddMovieFragment : Fragment(R.layout.fragment_add_movie) {

    private var _binding: FragmentAddMovieBinding? = null
    private val binding get() = _binding!!
    private val customMovieViewModel by viewModels<AddMovieViewModel>()

    private var imageUri: Uri? = null

    private val launchGalleryImageLoad =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                imageUri = it.data?.data
                try {

                    Glide.with(requireContext())
                        .load(imageUri)
                        .into(binding.imageViewCover)

                } catch (exception: FileNotFoundException) {
                    exception.printStackTrace()
                }
            }
        }
    private var launchPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                openGallery()
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddMovieBinding.bind(view)
        binding.apply {
            buttonAddCover.setOnClickListener {
                checkForPermission()
            }

            buttonAddMovie.setOnClickListener {
                insertCustomMovie()
            }
        }
    }

    private fun insertCustomMovie() {
        if (validateData()) {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val customMovie = CustomMovie(null, imageUri.toString(), title, description)
            customMovieViewModel.insertCustomMovie(customMovie)
            Toast.makeText(requireContext(), "Movie $title saved.", Toast.LENGTH_SHORT).show()
            NavHostFragment.findNavController(this).navigateUp()
        } else {
            Toast.makeText(
                requireContext(),
                "Check if cover image is not added or title and description are empty",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun validateData(): Boolean {
        return binding.editTextTitle.text.toString()
            .isNotEmpty() && binding.editTextDescription.text.toString()
            .isNotEmpty() && binding.imageViewCover.drawable != null
    }

    private fun checkForPermission() {
        launchPermissionRequest.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launchGalleryImageLoad.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}