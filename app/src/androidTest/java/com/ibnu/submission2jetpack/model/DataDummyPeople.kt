package com.ibnu.submission2jetpack.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataDummyPeople(
    val peopleID: String? = null,
    val peopleName: String? = null,
    val peopleBirthDate: String? = null,
    val peopleDeathMovie: String? = null,
    val peopleDescription: String? = null
) : Parcelable
