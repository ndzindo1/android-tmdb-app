package com.ndzindo.tmdb.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "custom_movie_table")
data class CustomMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val image: String,
    val title: String,
    val description: String
) : Parcelable