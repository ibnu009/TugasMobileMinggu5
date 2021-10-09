package com.ibnu.submission2jetpack.data.source.remote.status.movie

import com.ibnu.submission2jetpack.data.source.model.Movie

interface ResponseStatusMovieDetail {
    fun onSuccess(movie: Movie)
    fun onFailed()
}