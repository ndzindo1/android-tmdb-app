package com.ndzindo.tmdb.ui.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ndzindo.tmdb.data.CustomMovie
import com.ndzindo.tmdb.data.CustomMovieRepository
import com.ndzindo.tmdb.data.Movie
import com.ndzindo.tmdb.data.MovieRepository

class MovieListViewModel @ViewModelInject constructor(
    private val repository: MovieRepository,
    private val customMovieRepository: CustomMovieRepository
) : ViewModel() {

    companion object {
        private const val LIST_TYPE_POPULAR = "popular"
    }

    private val currentListType = MutableLiveData(LIST_TYPE_POPULAR)

    val movies = currentListType.switchMap { newListType ->
        repository.getMovieList(newListType).cachedIn(viewModelScope)
    }

    var favoriteMovies: LiveData<List<Movie>> = repository.getFavouriteMovies()

    var customMovies: LiveData<List<CustomMovie>> = customMovieRepository.getAllCustomMovies()

    fun getMovies(listType: String) {
        currentListType.value = listType
    }

}