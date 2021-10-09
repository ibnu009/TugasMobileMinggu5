package com.ibnu.submission2jetpack.data.source.model

import com.google.gson.annotations.SerializedName

data class TvShow(
    @field:SerializedName("id")
    val tvShowId: Int,
    @field:SerializedName("name")
    val tvShowName: String,
    @field:SerializedName("genre_ids")
    val tvShowCategory: List<Int>,
    @field:SerializedName("genres")
    val tvShowDetailCategory: List<Genres>,
    @field:SerializedName("backdrop_path")
    val tvShowBackdropPath: String,
    @field:SerializedName("poster_path")
    val tvShowPosterPath: String,
    @field:SerializedName("overview")
    val tvShowDescription: String,
    @field:SerializedName("seasons")
    val tvShowSeason: List<Season>,
    @field:SerializedName("vote_average")
    val tvShowAverageRating: Double,
    @field:SerializedName("vote_count")
    val tvShowVoteCount: Int
)
