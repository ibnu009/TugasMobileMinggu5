package com.ibnu.submission2jetpack.data.source.remote.status.tvshow

import com.ibnu.submission2jetpack.data.source.model.TvShow

interface ResponseStatusTvShowDetail {
    fun onSuccess(tvShow: TvShow)
    fun onFailed(message: String)
}