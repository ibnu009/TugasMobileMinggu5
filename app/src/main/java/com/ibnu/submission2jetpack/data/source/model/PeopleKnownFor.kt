package com.ibnu.submission2jetpack.data.source.model

import com.google.gson.annotations.SerializedName

data class PeopleKnownFor(
    @field:SerializedName("media_type")
    val mediaType: String,
    @field:SerializedName("name")
    val knownForTvShowName: String? = null,
    @field:SerializedName("title")
    val knownForMovieName: String? = null,
    )
