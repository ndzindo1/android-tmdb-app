package com.ndzindo.tmdb.data

import androidx.lifecycle.LiveData
import com.ndzindo.tmdb.cache.db.CustomMovieDao
import javax.inject.Inject

class CustomMovieRepository @Inject constructor(private val customMovieDao: CustomMovieDao) {

    suspend fun insertCustomMovie(customMovie: CustomMovie) {
        customMovieDao.insertCustomMovie(customMovie)
    }

    fun getAllCustomMovies(): LiveData<List<CustomMovie>> {
        return customMovieDao.getAllCustomMovies()
    }

}