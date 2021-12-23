package com.ndzindo.tmdb.ui.details.apimovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndzindo.tmdb.data.Movie
import com.ndzindo.tmdb.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    lateinit var movieEntry: LiveData<Movie>

    fun getMovieEntry(id: Int) {
        movieEntry = repository.getFavouriteMovieById(id)
    }

    fun addMovieToFavourite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovieToFavorite(movie)
        }
    }

    fun removeMovieFromFavourites(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeMovieFromFavorite(movie)
        }
    }

}