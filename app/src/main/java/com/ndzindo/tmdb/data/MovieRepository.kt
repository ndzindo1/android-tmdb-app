package com.ndzindo.tmdb.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ndzindo.tmdb.api.MovieApi
import com.ndzindo.tmdb.cache.db.MovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) {

    fun getMovieList(listType: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, movieDao, listType) }
        ).liveData

    fun getFavouriteMovieById(id: Int): LiveData<Movie> {
        return movieDao.getMovieById(id)
    }

    suspend fun addMovieToFavorite(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun removeMovieFromFavorite(movie: Movie) {
        movieDao.deleteMovie(movie)
    }

    fun getFavouriteMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }
}