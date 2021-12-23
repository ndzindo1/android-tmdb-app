package com.ndzindo.tmdb.data

import androidx.paging.PagingSource
import com.ndzindo.tmdb.api.MovieApi
import com.ndzindo.tmdb.cache.db.MovieDao
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 1

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val listType: String
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: START_PAGE_INDEX

        return try {
            val response = movieApi.getMovies(listType, position, MovieApi.CLIENT_ID)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == START_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}