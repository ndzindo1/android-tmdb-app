package com.ndzindo.tmdb.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ndzindo.tmdb.data.CustomMovie
import com.ndzindo.tmdb.data.Movie

@Database(entities = [Movie::class, CustomMovie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getCustomMovieDao(): CustomMovieDao
}