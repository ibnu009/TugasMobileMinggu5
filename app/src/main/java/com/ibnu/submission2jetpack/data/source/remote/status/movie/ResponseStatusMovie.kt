package com.ibnu.submission2jetpack.data.source.remote.status.movie

import com.ibnu.submission2jetpack.data.source.model.Movie

interface ResponseStatusMovie {
    fun onSuccess(list: List<Movie>)
    fun onFailed()
}