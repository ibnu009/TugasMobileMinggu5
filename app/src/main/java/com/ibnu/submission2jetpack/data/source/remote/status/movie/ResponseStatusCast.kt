package com.ibnu.submission2jetpack.data.source.remote.status.movie

import com.ibnu.submission2jetpack.data.source.model.Cast

interface ResponseStatusCast {
    fun onSuccess(casts: List<Cast>)
    fun onFailed(message: String)
}