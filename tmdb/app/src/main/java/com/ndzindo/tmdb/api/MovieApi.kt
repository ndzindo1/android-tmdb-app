package com.ndzindo.tmdb.api

import com.ndzindo.tmdb.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val CLIENT_ID = BuildConfig.TMDB_API_KEY
    }

    @GET("movie/{type}")
    suspend fun getMovies(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = CLIENT_ID
    ): MoviesResponse
}