package com.ibnu.submission2jetpack.data.paging.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.utils.EspressoIdlingResource
import com.ibnu.submission2jetpack.utils.NetworkState
import com.ibnu.submission2jetpack.utils.TypeUtils.POPULAR
import com.ibnu.submission2jetpack.utils.TypeUtils.TOP_RATED
import com.ibnu.submission2jetpack.webservice.RetrofitApp.Companion.FIRST_PAGE
import com.ibnu.submission2jetpack.webservice.jsonobject.Result
import com.ibnu.submission2jetpack.webservice.movie.MovieService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDataSource(
    private val apiService: MovieService,
    private val scope: CoroutineScope,
    private val type: String
) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE
    private val language = "en-US"

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
//        Mengubah Observable sebagai sumber data sesuai dengan parameter "type"
        EspressoIdlingResource.increment()
        scope.launch(Dispatchers.IO) {
            networkState.postValue(NetworkState.LOADING)
            try {
                var response = apiService.getPopularMovies(language, page)
                when (type) {
                    POPULAR -> response = apiService.getPopularMovies(language, page)
                    TOP_RATED -> response = apiService.getTopRatedMovies(language, page)
                }
                val listMovies = response.result
                callback.onResult(listMovies, null, page + 1)
                EspressoIdlingResource.decrement()
                networkState.postValue(NetworkState.LOADED)
            } catch (exception: Exception) {
                networkState.postValue(NetworkState.ERROR)
                exception.printStackTrace()
                Timber.e("MovieDataSource is Failed")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        EspressoIdlingResource.increment()
        networkState.postValue(NetworkState.LOADING)

        EspressoIdlingResource.increment()
        scope.launch(Dispatchers.IO) {
            networkState.postValue(NetworkState.LOADING)
            try {
                var response = apiService.getPopularMovies(language, page)
                when (type) {
                    POPULAR -> response = apiService.getPopularMovies(language, page)
                    TOP_RATED -> response = apiService.getTopRatedMovies(language, page)
                }
                val listMovies = response.result
                callback.onResult(listMovies ?: listOf(), params.key + 1)
                EspressoIdlingResource.decrement()
                networkState.postValue(NetworkState.LOADED)
            } catch (exception: Exception) {
                networkState.postValue(NetworkState.ERROR)
                Timber.e("MovieDataSource is Failed")
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}