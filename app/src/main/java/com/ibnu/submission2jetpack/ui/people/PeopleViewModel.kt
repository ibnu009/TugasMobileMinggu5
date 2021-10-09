package com.ibnu.submission2jetpack.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.source.model.People
import com.ibnu.submission2jetpack.data.repo.PeopleRepository
import com.ibnu.submission2jetpack.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val disposable: CompositeDisposable) : ViewModel() {

    fun getPeople(): LiveData<PagedList<People>>{
        return peopleRepository.getPeopleData(disposable)
    }

    fun listIsEmpty():Boolean {
        return getPeople().value?.isEmpty() ?: true
    }

    val networkState: LiveData<NetworkState> by lazy {
        peopleRepository.getNetworkState()
    }

}