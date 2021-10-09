package com.ibnu.submission2jetpack.data.source.remote.status.tvshow

import com.ibnu.submission2jetpack.data.source.model.TvShow

interface ResponseStatusTvShow {
    fun onSuccess(list: List<TvShow>)
    fun onFailed(message: String)
}