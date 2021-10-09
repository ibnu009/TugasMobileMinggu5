package com.ibnu.submission2jetpack.data.source.model

import com.google.gson.annotations.SerializedName

data class Season(
    @field:SerializedName("id")
    val seasonId: Int,
    @field:SerializedName("air_date")
    val seasonDate: String? = null,
    @field:SerializedName("name")
    val seasonName: String,
    @field:SerializedName("episode_count")
    val seasonEpisodeCount: Int,
    @field:SerializedName("overview")
    val seasonDesc: String,
    @field:SerializedName("poster_path")
    val seasonPoster: String,
)
