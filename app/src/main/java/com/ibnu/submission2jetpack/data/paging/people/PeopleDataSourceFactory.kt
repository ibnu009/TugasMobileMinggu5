package com.ibnu.submission2jetpack.data.paging.people

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ibnu.submission2jetpack.data.source.model.People
import com.ibnu.submission2jetpack.webservice.person.PeopleService
import io.reactivex.disposables.CompositeDisposable

class PeopleDataSourceFactory(
    private val apiService: PeopleService,
    private val compositeDisposable: CompositeDisposable
): DataSource.Factory<Int, People>() {

    val peopleLiveDataSource = MutableLiveData<PeopleDataSource>()

    override fun create(): DataSource<Int, People> {
        val tvDataSource = PeopleDataSource(apiService,compositeDisposable)

        peopleLiveDataSource.postValue(tvDataSource)
        return tvDataSource
    }
}