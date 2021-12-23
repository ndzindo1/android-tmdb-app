package com.ndzindo.tmdb.ui.addmovie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndzindo.tmdb.data.CustomMovie
import com.ndzindo.tmdb.data.CustomMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMovieViewModel @ViewModelInject constructor(private val customMovieRepository: CustomMovieRepository) :
    ViewModel() {

    fun insertCustomMovie(customMovie: CustomMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            customMovieRepository.insertCustomMovie(customMovie)
        }
    }
}