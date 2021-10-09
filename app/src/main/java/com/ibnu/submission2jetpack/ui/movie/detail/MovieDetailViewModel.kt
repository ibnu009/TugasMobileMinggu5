package com.ibnu.submission2jetpack.ui.movie.detail

import androidx.lifecycle.*
import com.ibnu.submission2jetpack.data.repo.FavoriteRepository
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.Movie
import com.ibnu.submission2jetpack.data.repo.MovieRepository
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovie
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusMovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {
//
//    private var movieDetail = MutableLiveData<Movie>()
//    private val movieCasts = MutableLiveData<List<Cast>>()
//    private val similarMovies = MutableLiveData<List<Movie>>()
//    private val recommendedMovies = MutableLiveData<List<Movie>>()

    fun getDetailMovie(movieId: Int): LiveData<Movie> {
        return movieRepository.getMovieDetail(movieId, viewModelScope)
    }

    fun getCasts(movieId: Int): LiveData<List<Cast>> {
        return movieRepository.getCastsMovie(movieId, viewModelScope)
    }

    fun getSimilarMovies(movieId: Int): LiveData<List<Movie>> {
        return movieRepository.getSimilarMovies(movieId, viewModelScope)
    }

    fun getRecommendedMovies(movieId: Int): LiveData<List<Movie>> {
        return movieRepository.getRecommendedMovies(movieId, viewModelScope)
    }

    fun insertToFavorite(favoriteItem: FavoriteItemEntity) {
        favoriteRepository.insertItemIntoFavorite(favoriteItem)
        Timber.d("Data Inserted")
    }

    fun checkIsFavorite(id: Int): LiveData<Int> {
        return favoriteRepository.checkIsItemFavorite(id)
    }

    fun deleteMovieFromFavorite(id: Int) {
        favoriteRepository.deleteItemFromFavorite(id)
        Timber.d("Movie Deleted")
    }
}