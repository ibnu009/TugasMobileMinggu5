package com.ibnu.submission2jetpack.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ibnu.submission2jetpack.data.repo.MovieRepository
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.ui.movie.MovieViewModel
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<List<Movie>>

    @Mock
    lateinit var movieRepo: MovieRepository

    @Mock
    lateinit var disposable: CompositeDisposable

    private lateinit var viewModel: MovieViewModel
    private var apiService = RetrofitApp.getMovieApiService()
    private val language = "en-US"
    private val page = 1
    private val popular = "popular"
    private val topRated = "top rated"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieViewModel(movieRepo, disposable)
    }

    @Test
    fun getPopularMovies() {
        viewModel.getMovies(popular)

        apiService.getPopularMovies(language, page)
            .map {
                it.result
            }.subscribe(
                {
                    viewModel.getMovies(popular).observeForever(observer)
                    verify(observer).onChanged(it)
                },{}
            )
    }

    @Test
    fun getTopRatedMovies() {
        viewModel.getMovies(topRated)

        apiService.getTopRatedMovies(language, page).map {
            it.result
        }.subscribe(
            {
                viewModel.getMovies(topRated).observeForever(observer)
                verify(observer).onChanged(it)
            },{}
        )
    }
}