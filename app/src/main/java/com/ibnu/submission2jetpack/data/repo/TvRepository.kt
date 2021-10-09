package com.ibnu.submission2jetpack.data.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.paging.ResponsePagedTv
import com.ibnu.submission2jetpack.data.paging.tv.TvShowDataSourceFactory
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShow
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShowDetail
import com.ibnu.submission2jetpack.data.source.remote.tv.TvShowDataSource
import com.ibnu.submission2jetpack.utils.EspressoIdlingResource
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import com.ibnu.submission2jetpack.webservice.RetrofitApp.Companion.ITEM_PER_PAGE
import com.ibnu.submission2jetpack.webservice.tvshow.TvShowService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class TvRepository @Inject constructor(
    context: Context,
    private val apiService: TvShowService) : TvShowDataSource {

//    private val apiService = RetrofitApp.getTVShowApiService()
    private val language = "en-US"


    override fun getTvShowData(
        type: String,
        composite: CompositeDisposable,
        status: ResponsePagedTv
    ): LiveData<PagedList<TvShow>> {
        lateinit var tvResult: LiveData<PagedList<TvShow>>
        val tvDataSourceFactory = TvShowDataSourceFactory(apiService, composite, type)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ITEM_PER_PAGE)
            .build()

        tvResult = LivePagedListBuilder(tvDataSourceFactory, config).build()
        Timber.d("Size of Tv is ${tvResult.value?.size}")
        return tvResult
    }

    override fun getTvDetail(
        tvId: Int,
        composite: CompositeDisposable,
        status: ResponseStatusTvShowDetail
    ): LiveData<TvShow> {
        EspressoIdlingResource.increment()

        val tvDetailResult = MutableLiveData<TvShow>()
        composite.add(
            apiService.getTvShowDetail(
                tvId = tvId.toString(),
                language = language
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    status.onSuccess(it)
                    tvDetailResult.postValue(it)
                    EspressoIdlingResource.decrement()

                }, {
                    it.message?.let { it1 -> status.onFailed(it1) }
                })
        )
        return tvDetailResult
    }

    override fun getTvCast(
        tvId: Int,
        composite: CompositeDisposable,
        statusCast: ResponseStatusCast
    ): LiveData<List<Cast>> {
        EspressoIdlingResource.increment()

        val castResult = MutableLiveData<List<Cast>>()
        composite.add(apiService.getTvShowCast(tvId.toString(),language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.listCasts
            }.subscribe(
                {
                    statusCast.onSuccess(it)
                    castResult.postValue(it)
                    EspressoIdlingResource.decrement()
                }, {
                    it.message?.let { it1 -> statusCast.onFailed(it1) }
                }
            ))
        return castResult
    }

    override fun getSimilarTvShow(
        tvId: Int,
        composite: CompositeDisposable,
        status: ResponseStatusTvShow
    ): LiveData<List<TvShow>> {
        EspressoIdlingResource.increment()
        val similarTvShowResult = MutableLiveData<List<TvShow>>()
        composite.add(apiService.getSimilarTvShow(
            tvId.toString(),
            language
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.result
            }.subscribe(
                {
                    status.onSuccess(it)
                    similarTvShowResult.postValue(it)
                    EspressoIdlingResource.decrement()
                }, {
                    it.message?.let { it1 -> status.onFailed(it1) }
                }
            ))
        return similarTvShowResult
    }

    override fun getRecommendedTvShow(
        tvId: Int,
        composite: CompositeDisposable,
        status: ResponseStatusTvShow
    ): LiveData<List<TvShow>> {
        EspressoIdlingResource.increment()

        val recommendedTvShowResult = MutableLiveData<List<TvShow>>()
        composite.add(apiService.getRecommendedTvShow(
            tvId.toString(),
            language
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.result
            }.subscribe(
                {
                    status.onSuccess(it)
                    recommendedTvShowResult.postValue(it)
                    EspressoIdlingResource.decrement()

                }, {
                    it.message?.let { it1 -> status.onFailed(it1) }
                }
            ))
        return recommendedTvShowResult
    }

}