package com.ibnu.submission2jetpack.data.source.remote.tv

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.paging.ResponsePagedTv
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShow
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShowDetail
import io.reactivex.disposables.CompositeDisposable

interface TvShowDataSource {
    fun getTvShowData(type: String, composite: CompositeDisposable, status: ResponsePagedTv): LiveData<PagedList<TvShow>>
    fun getTvDetail(tvId: Int, composite: CompositeDisposable, status: ResponseStatusTvShowDetail): LiveData<TvShow>
    fun getTvCast(tvId: Int, composite: CompositeDisposable, statusCast: ResponseStatusCast): LiveData<List<Cast>>
    fun getSimilarTvShow(tvId: Int, composite: CompositeDisposable, status: ResponseStatusTvShow): LiveData<List<TvShow>>
    fun getRecommendedTvShow(tvId: Int, composite: CompositeDisposable, status: ResponseStatusTvShow): LiveData<List<TvShow>>
}