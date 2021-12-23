package com.ndzindo.tmdb.di

import android.content.Context
import androidx.room.Room
import com.ndzindo.tmdb.api.MovieApi
import com.ndzindo.tmdb.cache.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase) =
        db.getMovieDao()

    @Provides
    @Singleton
    fun provideCustomMovieDao(db: MovieDatabase) =
        db.getCustomMovieDao()
}