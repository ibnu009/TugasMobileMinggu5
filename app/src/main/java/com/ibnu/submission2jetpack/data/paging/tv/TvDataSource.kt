package com.ibnu.submission2jetpack.data.paging.tv

import androidx.paging.PageKeyedDataSource
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.utils.EspressoIdlingResource
import com.ibnu.submission2jetpack.utils.TypeUtils.POPULAR
import com.ibnu.submission2jetpack.utils.TypeUtils.TOP_RATED
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import com.ibnu.submission2jetpack.webservice.jsonobject.ResultTvShow
import com.ibnu.submission2jetpack.webservice.tvshow.TvShowService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TvDataSource(
    private val apiService: TvShowService,
    private val compositeDisposable: CompositeDisposable,
    private val type: String): PageKeyedDataSource<Int, TvShow>() {

    private var page = RetrofitApp.FIRST_PAGE
    private val language = "en-US"


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShow>
    ) {
        EspressoIdlingResource.increment()

        var observable: Observable<ResultTvShow> = apiService.getPopularTvShow(language, page)
        when (type) {
            POPULAR -> observable = apiService.getPopularTvShow(language, page)
            TOP_RATED -> observable = apiService.getTopRatedTvShow(language, page)
        }
        compositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .map {
                    it.result
                }.subscribe({
                    Timber.d("Size is ${it.size}")
                    callback.onResult(it, null, page + 1)
                    EspressoIdlingResource.decrement()

                },{
                    Timber.e("Error : $it")
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShow>) {
        EspressoIdlingResource.increment()

        var observable: Observable<ResultTvShow> = apiService.getPopularTvShow(language, params.key)
        when (type) {
            POPULAR -> observable = apiService.getPopularTvShow(language, params.key)
            TOP_RATED -> observable = apiService.getTopRatedTvShow(language, params.key)
        }
        compositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPage >= params.key) {
                        Timber.d("Size is ${it.result.size}")
                        callback.onResult(it.result, params.key + 1)
                        EspressoIdlingResource.decrement()

                    }
                }, {
                    Timber.e("Error : $it")
                })
        )
    }
}