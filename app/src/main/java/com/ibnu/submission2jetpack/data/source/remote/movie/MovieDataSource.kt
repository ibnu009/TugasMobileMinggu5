package com.ibnu.submission2jetpack.data.source.remote.movie

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.paging.ResponsePagedMovie
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovie
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovieDetail
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

interface MovieDataSource {
     fun getMoviesData(type: String, scope: CoroutineScope, status: ResponsePagedMovie): LiveData<PagedList<Movie>>
     fun getMovieDetail(movieId: Int, scope: CoroutineScope): LiveData<Movie>
     fun getCastsMovie(movieId: Int, scope: CoroutineScope): LiveData<List<Cast>>
     fun getSimilarMovies(movieId: Int, scope: CoroutineScope): LiveData<List<Movie>>
     fun getRecommendedMovies(movieId: Int, scope: CoroutineScope): LiveData<List<Movie>>
}