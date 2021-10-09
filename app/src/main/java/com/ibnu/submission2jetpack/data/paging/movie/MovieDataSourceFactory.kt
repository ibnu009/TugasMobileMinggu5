package com.ibnu.submission2jetpack.data.paging.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.webservice.movie.MovieService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

class MovieDataSourceFactory(
    private val apiService: MovieService,
    private val scope: CoroutineScope,
    private val type: String
) : DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, scope, type)

        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}