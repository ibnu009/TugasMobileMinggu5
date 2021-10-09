package com.ibnu.submission2jetpack.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test

import androidx.lifecycle.Observer
import com.ibnu.submission2jetpack.data.repo.FavoriteRepository
import com.ibnu.submission2jetpack.data.repo.MovieRepository
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovie
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovieDetail
import com.ibnu.submission2jetpack.ui.movie.detail.MovieDetailViewModel
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerDetailMovie: Observer<Movie>

    @Mock
    lateinit var observerRecommendation: Observer<List<Movie>>

    @Mock
    lateinit var observerSimilar: Observer<List<Movie>>

    @Mock
    lateinit var observerCast: Observer<List<Cast>>

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var favoriteRepository: FavoriteRepository

    @Mock
    lateinit var disposable: CompositeDisposable

    private var apiService = RetrofitApp.getMovieApiService()
    private lateinit var detailViewModel: MovieDetailViewModel

    private val language = "en-US"
    private val sampleMovieId = 238 //The GodFather

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailViewModel = MovieDetailViewModel(movieRepository, favoriteRepository, disposable)
    }

    @Test
    fun getDetailMovie() {
        detailViewModel.getDetailMovie(sampleMovieId)

        apiService.getMovieDetailAsync(sampleMovieId.toString(), language).subscribe(
            {
                argumentCaptor<ResponseStatusMovieDetail>().apply {
                    Mockito.verify(movieRepository)
                        .getMovieDetail(sampleMovieId, disposable, capture())
                    firstValue.onSuccess(it)
                }
                detailViewModel.getDetailMovie(sampleMovieId).observeForever(observerDetailMovie)
                verify(observerDetailMovie).onChanged(it)
            }, {}
        )
    }

    @Test
    fun getCasts() {
        detailViewModel.getCasts(sampleMovieId)

        apiService.getCredits(sampleMovieId.toString(), language).map { it.listCasts }
            .subscribe({
                argumentCaptor<ResponseStatusCast>().apply {
                    Mockito.verify(movieRepository)
                        .getCastsMovie(sampleMovieId, disposable, capture())
                    firstValue.onSuccess(it)
                }
                detailViewModel.getCasts(sampleMovieId).observeForever(observerCast)
                verify(observerCast).onChanged(it)
            }, {

            })
    }

    @Test
    fun getSimilarMovies() {
        detailViewModel.getSimilarMovies(sampleMovieId)

        apiService.getSimilarMovies(sampleMovieId.toString(),language).map { it.result }
            .subscribe(
                {
                    argumentCaptor<ResponseStatusMovie>().apply {
                        Mockito.verify(movieRepository)
                            .getSimilarMovies(sampleMovieId, disposable, capture())
                        firstValue.onSuccess(it)
                    }
                    detailViewModel.getSimilarMovies(sampleMovieId).observeForever(observerSimilar)
                    verify(observerSimilar).onChanged(it)
                }, {

                }
            )
    }

    @Test
    fun getRecommendedMovies() {
        detailViewModel.getRecommendedMovies(sampleMovieId)

        apiService.getRecommendedMovies(sampleMovieId.toString(), language)
            .map { it.result }.subscribe(
                {
                    argumentCaptor<ResponseStatusMovie>().apply {
                        Mockito.verify(movieRepository)
                            .getRecommendedMovies(sampleMovieId, disposable, capture())
                        firstValue.onSuccess(it)
                    }
                    detailViewModel.getRecommendedMovies(sampleMovieId).observeForever(observerRecommendation)
                    verify(observerRecommendation).onChanged(it)
                }, {}
            )
    }
}