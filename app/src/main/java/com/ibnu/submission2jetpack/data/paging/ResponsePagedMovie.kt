package com.ibnu.submission2jetpack.data.paging

import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.source.model.Movie

interface ResponsePagedMovie {
    fun onSuccess(list: PagedList<Movie>)
    fun onFailed()
}