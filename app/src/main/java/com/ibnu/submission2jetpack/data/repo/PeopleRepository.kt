package com.ibnu.submission2jetpack.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ibnu.submission2jetpack.data.paging.people.PeopleDataSource
import com.ibnu.submission2jetpack.data.paging.people.PeopleDataSourceFactory
import com.ibnu.submission2jetpack.data.source.model.People
import com.ibnu.submission2jetpack.utils.NetworkState
import com.ibnu.submission2jetpack.webservice.RetrofitApp
import io.reactivex.disposables.CompositeDisposable


class PeopleRepository {

    private lateinit var peopleDataSourceFactory: PeopleDataSourceFactory
    private val apiService = RetrofitApp.getPeopleApiService()


     fun getPeopleData(disposable: CompositeDisposable): LiveData<PagedList<People>> {
        lateinit var peopleResult: LiveData<PagedList<People>>

        peopleDataSourceFactory = PeopleDataSourceFactory(apiService, disposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(RetrofitApp.ITEM_PER_PAGE)
            .build()

        peopleResult = LivePagedListBuilder(peopleDataSourceFactory, config).build()
        return peopleResult
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            peopleDataSourceFactory.peopleLiveDataSource,
            PeopleDataSource::networkState
        )
    }
}