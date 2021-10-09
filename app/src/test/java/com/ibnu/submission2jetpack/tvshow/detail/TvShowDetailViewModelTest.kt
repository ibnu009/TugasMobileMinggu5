package com.ibnu.submission2jetpack.tvshow.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ibnu.submission2jetpack.data.repo.FavoriteRepository
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShow
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShowDetail
import com.ibnu.submission2jetpack.data.repo.TvRepository
import com.ibnu.submission2jetpack.ui.tvshow.detail.TvShowDetailViewModel
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TvShowDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observerTvDetail: Observer<TvShow>

    @Mock
    private lateinit var observerSimilarTvShow: Observer<List<TvShow>>

    @Mock
    private lateinit var observerRecommendedTvShow: Observer<List<TvShow>>

    @Mock
    private lateinit var observerCast: Observer<List<Cast>>

    @Mock
    private lateinit var tvRepository: TvRepository

    @Mock
    private lateinit var favoriteRepository: FavoriteRepository

    @Mock
    private lateinit var disposable: CompositeDisposable

    private lateinit var viewModelDetailTvShow: TvShowDetailViewModel
    private var apiService = RetrofitApp.getTVShowApiService()
    private val language = "en-US"
    private val sampleTvId = 82856

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModelDetailTvShow = TvShowDetailViewModel(tvRepository,favoriteRepository, disposable)
    }

    @Test
    fun getTvShowDetail() {
        viewModelDetailTvShow.getTvShowDetail(sampleTvId)

        apiService.getTvShowDetail(sampleTvId.toString(), language).subscribe({
            argumentCaptor<ResponseStatusTvShowDetail>().apply {
                Mockito.verify(tvRepository).getTvDetail(sampleTvId, disposable, capture())
                firstValue.onSuccess(it)
            }
            viewModelDetailTvShow.getTvShowDetail(sampleTvId).observeForever(observerTvDetail)
            verify(observerTvDetail).onChanged(it)
        }, {
        })
    }

    @Test
    fun getTvShowCast() {
        viewModelDetailTvShow.getTvShowCast(sampleTvId)

        apiService.getTvShowCast(sampleTvId.toString(), language).map { it.listCasts }.subscribe(
            {
            argumentCaptor<ResponseStatusCast>().apply {
                Mockito.verify(tvRepository).getTvCast(sampleTvId, disposable, capture())
                firstValue.onSuccess(it)
            }
                viewModelDetailTvShow.getTvShowCast(sampleTvId).observeForever(observerCast)
                verify(observerCast).onChanged(it)
            },{}
        )
    }

    @Test
    fun getSimilarTvShow() {
        viewModelDetailTvShow.getSimilarTvShow(sampleTvId)

        apiService.getSimilarTvShow(sampleTvId.toString(),language).map { it.result }.subscribe(
            {
            argumentCaptor<ResponseStatusTvShow>().apply {
                Mockito.verify(tvRepository).getSimilarTvShow(sampleTvId, disposable, capture())
                firstValue.onSuccess(it)
            }
                viewModelDetailTvShow.getSimilarTvShow(sampleTvId).observeForever(observerSimilarTvShow)
                verify(observerSimilarTvShow).onChanged(it)
            },{}
        )
    }

    @Test
    fun getRecommendedTvShow() {
        viewModelDetailTvShow.getRecommendedTvShow(sampleTvId)

        apiService.getRecommendedTvShow(sampleTvId.toString(), language).map { it.result }.subscribe(
            {
                argumentCaptor<ResponseStatusTvShow>().apply {
                    Mockito.verify(tvRepository).getRecommendedTvShow(sampleTvId, disposable, capture())
                    firstValue.onSuccess(it)
                }
                viewModelDetailTvShow.getRecommendedTvShow(sampleTvId).observeForever(observerRecommendedTvShow)
                verify(observerRecommendedTvShow).onChanged(it)
            },{}
        )
    }
}