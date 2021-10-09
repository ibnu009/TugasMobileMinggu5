package com.ibnu.submission2jetpack.data.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.paging.movie.MovieDataSourceFactory
import com.ibnu.submission2jetpack.data.paging.ResponsePagedMovie
import com.ibnu.submission2jetpack.data.source.local.room.FavoriteDao
import com.ibnu.submission2jetpack.data.source.local.room.db.DataBase
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.source.remote.movie.MovieDataSource
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovie
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovieDetail
import com.ibnu.submission2jetpack.utils.NetworkState
import com.ibnu.submission2jetpack.webservice.RetrofitApp.Companion.ITEM_PER_PAGE
import com.ibnu.submission2jetpack.webservice.movie.MovieService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MovieRepository @Inject constructor(
    context: Context,
    private val apiService: MovieService
) : MovieDataSource {
    //    private val apiService = RetrofitApp.getMovieApiService()
    private val favoriteDao: FavoriteDao

    init {
        val db = DataBase.getDataBase(context)
        favoriteDao = db.favoriteDao()
    }

    private val language = "en-US"
    private lateinit var movieDataSourceFactory: MovieDataSourceFactory

    override fun getMoviesData(
        type: String,
        scope: CoroutineScope,
        status: ResponsePagedMovie
    ): LiveData<PagedList<Movie>> {
        lateinit var movieResult: LiveData<PagedList<Movie>>
        movieDataSourceFactory = MovieDataSourceFactory(apiService, scope, type)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ITEM_PER_PAGE)
            .build()

        movieResult = LivePagedListBuilder(movieDataSourceFactory, config).build()
        return movieResult
    }

    override  fun getMovieDetail(
        movieId: Int, scope: CoroutineScope
    ): LiveData<Movie> {
        val movieDetailResult = MutableLiveData<Movie>()
        scope.launch(Dispatchers.IO){
            try {
                val movie = apiService.getMovieDetail(movieId.toString(), language)
                movieDetailResult.postValue(movie)
            }catch (e: Exception){
                Timber.e("Network Failure because of ${e.cause}")
            }
        }
        return movieDetailResult
    }

    override  fun getCastsMovie(
        movieId: Int, scope: CoroutineScope
    ): LiveData<List<Cast>> {
        val movieCastsResult = MutableLiveData<List<Cast>>()
        scope.launch(Dispatchers.IO) {
            try {
                val casts = apiService.getCredits(movieId.toString(), language).listCasts
                movieCastsResult.postValue(casts)
            } catch (e: Exception) {
                Timber.e("Network Failure because of ${e.cause}")
            }
        }
        return movieCastsResult
    }

    override  fun getSimilarMovies(
        movieId: Int, scope: CoroutineScope
    ): LiveData<List<Movie>> {
        val similarMoviesResult = MutableLiveData<List<Movie>>()
        scope.launch(Dispatchers.IO) {
            try {
                val similarMovies = apiService.getSimilarMovies(movieId.toString(), language).result
                similarMoviesResult.postValue(similarMovies)
            } catch (e: Exception) {
                Timber.e("Network Failure because of ${e.cause}")
            }
        }
        return similarMoviesResult
    }

    override fun getRecommendedMovies(
        movieId: Int, scope: CoroutineScope
    ): LiveData<List<Movie>> {
        val recommendedMoviesResult = MutableLiveData<List<Movie>>()
        scope.launch(Dispatchers.IO) {
            try {
                val recommendedMovies =
                    apiService.getRecommendedMovies(movieId.toString(), language).result
                recommendedMoviesResult.postValue(recommendedMovies)
            } catch (e: Exception) {
                Timber.e("Network Failure because of ${e.cause}")
            }
        }
        return recommendedMoviesResult
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            movieDataSourceFactory.movieLiveDataSource,
            com.ibnu.submission2jetpack.data.paging.movie.MovieDataSource::networkState
        )
    }

}
