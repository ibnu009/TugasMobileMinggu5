package com.ibnu.submission2jetpack.data.source.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @field:SerializedName("id")
    val castId: Int,
    @field:SerializedName("name")
    val castName: String,
    @field:SerializedName("gender")
    val castGender: Int,
    @field:SerializedName("profile_path")
    val castImage: String,
)