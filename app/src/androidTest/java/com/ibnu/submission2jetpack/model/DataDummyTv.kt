package com.ibnu.submission2jetpack.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataDummyTv(
    val tvID: String? = null,
    val tvName: String? = null,
    val tvDate: String? = null,
    val tvAverageRating: String? = null,
    val tvVoteCount: String? = null,
    val tvDescription: String? = null
) : Parcelable