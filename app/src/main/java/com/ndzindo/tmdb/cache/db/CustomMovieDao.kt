package com.ndzindo.tmdb.cache.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ndzindo.tmdb.data.CustomMovie

@Dao
interface CustomMovieDao {
    @Insert
    suspend fun insertCustomMovie(customMovie: CustomMovie)

    @Query("SELECT * FROM custom_movie_table")
    fun getAllCustomMovies(): LiveData<List<CustomMovie>>
}