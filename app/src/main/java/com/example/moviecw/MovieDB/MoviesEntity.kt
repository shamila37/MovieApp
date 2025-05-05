package com.example.moviecw.MovieDB

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//https://developer.android.com/kotlin/parcelize
@Parcelize // Use this to solve screen rotation problem (memory loss)
@Entity(tableName = "movies")
data class MoviesEntity (
    @PrimaryKey val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runTime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val imdbRating: String,
    val imdbVotes: String,
    val type: String,
    val totalSeasons: String,
    val response: String
) : Parcelable


//w1986643/20223147
//Shamila Ashan Gunarathna
