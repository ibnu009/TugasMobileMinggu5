package com.ibnu.submission2jetpack.data.paging.people

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ibnu.submission2jetpack.data.source.model.People
import com.ibnu.submission2jetpack.utils.EspressoIdlingResource
import com.ibnu.submission2jetpack.utils.NetworkState
import com.ibnu.submission2jetpack.webservice.RetrofitApp.Companion.FIRST_PAGE
import com.ibnu.submission2jetpack.webservice.jsonobject.ResultPeople
import com.ibnu.submission2jetpack.webservice.person.PeopleService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PeopleDataSource(
    private val apiService: PeopleService,
    private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, People>() {

    private var page = FIRST_PAGE
    private val language = "en-US"

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, People>
    ) {
        EspressoIdlingResource.increment()

        networkState.postValue(NetworkState.LOADING)
        val observable: Observable<ResultPeople> = apiService.getPeopleData(language, page)
        compositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .map {
                    it.result
                }.subscribe({
                    Timber.d("Size is ${it.size}")
                    callback.onResult(it, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                    EspressoIdlingResource.decrement()

                },{
                    networkState.postValue(NetworkState.ERROR)
                    Timber.e("Error : $it")
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, People>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, People>) {
        EspressoIdlingResource.increment()

        networkState.postValue(NetworkState.LOADING)

        val observable: Observable<ResultPeople> = apiService.getPeopleData(language, page)
        compositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPage >= params.key) {
                        Timber.d("Size is ${it.result.size}")
                        callback.onResult(it.result, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                        EspressoIdlingResource.decrement()

                    }
                }, {
                    networkState.postValue(NetworkState.ERROR)
                    Timber.e("Error : $it")
                })
        )
    }
}