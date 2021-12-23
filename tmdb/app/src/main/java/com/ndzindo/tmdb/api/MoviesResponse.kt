package com.ndzindo.tmdb.api

import com.ndzindo.tmdb.data.Movie

data class MoviesResponse(
    val results: List<Movie>
)