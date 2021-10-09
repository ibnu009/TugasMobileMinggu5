package com.ibnu.submission2jetpack.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataDummyMovie(
    val movieID: String? = null,
    val movieName: String? = null,
    val movieDate: String? = null,
    val statusMovie: String? = null,
    val movieAverageRating: String? = null,
    val movieVoteCount: String? = null,
    val movieDescription: String? = null
) : Parcelable