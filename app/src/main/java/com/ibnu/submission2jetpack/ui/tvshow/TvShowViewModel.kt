package com.ibnu.submission2jetpack.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.paging.ResponsePagedTv
import com.ibnu.submission2jetpack.data.source.model.TvShow
import com.ibnu.submission2jetpack.data.repo.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val tvRepository: TvRepository, private val composite: CompositeDisposable) : ViewModel() {

    fun getTvShows(type: String): LiveData<PagedList<TvShow>>{
        Timber.d("Check")
       return tvRepository.getTvShowData(type, composite, object: ResponsePagedTv{
            override fun onSuccess(list: PagedList<TvShow>) {
            }
            override fun onFailed() {
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

}