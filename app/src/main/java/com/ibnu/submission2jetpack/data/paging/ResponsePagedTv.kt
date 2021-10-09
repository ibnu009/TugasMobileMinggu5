package com.ibnu.submission2jetpack.data.paging

import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.source.model.TvShow

interface ResponsePagedTv {
    fun onSuccess(list: PagedList<TvShow>)
    fun onFailed()
}