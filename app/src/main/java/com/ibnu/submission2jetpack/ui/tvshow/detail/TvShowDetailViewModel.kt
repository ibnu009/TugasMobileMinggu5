package com.ibnu.submission2jetpack.ui.tvshow.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibnu.submission2jetpack.data.repo.FavoriteRepository
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import com.ibnu.submission2jetpack.data.source.model.Cast
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.source.remote.status.movie.ResponseStatusCast
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShow
import com.ibnu.submission2jetpack.data.source.remote.status.tvshow.ResponseStatusTvShowDetail
import com.ibnu.submission2jetpack.data.repo.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val tvRepository: TvRepository,
    private val favoriteRepository: FavoriteRepository,
    private val composite: CompositeDisposable
) : ViewModel() {

    private val tvShowDetail = MutableLiveData<TvShow>()
    private val tvShowCast = MutableLiveData<List<Cast>>()
    private val tvShowSimilar = MutableLiveData<List<TvShow>>()
    private val tvShowRecommended = MutableLiveData<List<TvShow>>()


    fun getTvShowDetail(tvId: Int): LiveData<TvShow> {
        tvRepository.getTvDetail(tvId, composite, object : ResponseStatusTvShowDetail {
            override fun onSuccess(tvShow: TvShow) {
                tvShowDetail.postValue(tvShow)
            }

            override fun onFailed(message: String) {
                Timber.e(message)
            }
        })
        return tvShowDetail
    }

    fun getTvShowCast(tvId: Int): LiveData<List<Cast>> {
        tvRepository.getTvCast(tvId, composite, object : ResponseStatusCast {
            override fun onSuccess(casts: List<Cast>) {
                tvShowCast.postValue(casts)
            }

            override fun onFailed(message: String) {
                Timber.e(message)
            }
        })
        return tvShowCast
    }

    fun getSimilarTvShow(tvId: Int): LiveData<List<TvShow>> {
        tvRepository.getSimilarTvShow(tvId, composite, object : ResponseStatusTvShow {
            override fun onSuccess(list: List<TvShow>) {
                tvShowSimilar.postValue(list)
            }

            override fun onFailed(message: String) {
                Timber.e(message)
            }
        })
        return tvShowSimilar
    }

    fun getRecommendedTvShow(tvId: Int): LiveData<List<TvShow>> {
        tvRepository.getRecommendedTvShow(tvId, composite, object : ResponseStatusTvShow {
            override fun onSuccess(list: List<TvShow>) {
                tvShowRecommended.postValue(list)
            }

            override fun onFailed(message: String) {
                Timber.e(message)
            }
        })
        return tvShowRecommended
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
        Timber.d("Tv Deleted")
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

}