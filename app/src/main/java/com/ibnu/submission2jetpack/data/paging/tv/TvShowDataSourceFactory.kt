package com.ibnu.submission2jetpack.data.paging.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.webservice.tvshow.TvShowService
import io.reactivex.disposables.CompositeDisposable

class TvShowDataSourceFactory(
    private val apiService: TvShowService,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
): DataSource.Factory<Int, TvShow>() {

    private val tvLiveDataSource = MutableLiveData<TvDataSource>()

    override fun create(): DataSource<Int, TvShow> {
        val tvDataSource = TvDataSource(apiService,compositeDisposable, type)

        tvLiveDataSource.postValue(tvDataSource)
        return tvDataSource
    }
}