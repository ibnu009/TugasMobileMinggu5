package com.ibnu.submission2jetpack.data.source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_movie")
data class Movie(
    @PrimaryKey
    @field:SerializedName("id")
    val movieId: Int,
    @field:SerializedName("title")
    val movieName: String,
    @field:SerializedName("genre_ids")
    val movieCategory: List<Int>,
    @field:SerializedName("genres")
    val movieDetailCategory: List<Genres>,
    @field:SerializedName("backdrop_path")
    val movieBackdropPath: String,
    @field:SerializedName("poster_path")
    val moviePosterPath: String,
    @field:SerializedName("overview")
    val movieDescription: String,
    @field:SerializedName("vote_average")
    val movieAverageRating: Double,
    @field:SerializedName("vote_count")
    val movieVoteCount: Int
)