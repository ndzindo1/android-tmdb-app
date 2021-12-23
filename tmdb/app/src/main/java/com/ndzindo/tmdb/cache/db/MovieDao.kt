package com.ndzindo.tmdb.cache.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ndzindo.tmdb.data.Movie

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movies WHERE id=:movieId")
    fun getMovieById(movieId: Int): LiveData<Movie>
}