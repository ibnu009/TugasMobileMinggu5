package com.ibnu.submission2jetpack.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.repo.TvRepository
import com.ibnu.submission2jetpack.ui.tvshow.TvShowViewModel
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<TvShow>>

    @Mock
    private lateinit var tvShowRepository: TvRepository

    @Mock
    private lateinit var disposable: CompositeDisposable

    private lateinit var viewModel: TvShowViewModel
    private var apiService = RetrofitApp.getTVShowApiService()
    private val language = "en-US"
    private val page = 1
    private val popular = "popular"
    private val topRated = "top rated"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TvShowViewModel(tvShowRepository, disposable)
    }

    @Test
    fun getTvShowsPopular() {
        viewModel.getTvShows(popular)

        apiService.getPopularTvShow(language, page).map { it.result }.subscribe({
            viewModel.getTvShows(popular).observeForever(observer)
            Mockito.verify(it)
        }, {})
    }

    @Test
    fun getTvShowsTopRated() {
        viewModel.getTvShows(topRated)

        apiService.getPopularTvShow(language, page).map { it.result }.subscribe({
            viewModel.getTvShows(topRated).observeForever(observer)
            Mockito.verify(it)
        }, {})
    }

}