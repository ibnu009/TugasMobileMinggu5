package com.ibnu.submission2jetpack.data.source.model

import com.google.gson.annotations.SerializedName

data class People(
    @field:SerializedName("id")
    val peopleId: Int,
    @field:SerializedName("gender")
    val peopleGender: Int,
    @field:SerializedName("name")
    val peopleName: String,
    @field:SerializedName("profile_path")
    val peopleImg: String,
    @field:SerializedName("biography")
    val peopleBio: String,
    @field:SerializedName("birthday")
    val peopleBirthday: String,
    @field:SerializedName("deathday")
    val peopleDeathDay: String? = null,
    @field:SerializedName("place_of_birth")
    val peoplePlaceOfBirth: String,
    @field:SerializedName("known_for")
    val knownFor: List<PeopleKnownFor>,
)
