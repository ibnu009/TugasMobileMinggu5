package com.ibnu.submission2jetpack.data.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ibnu.submission2jetpack.data.source.local.entity.FavoriteItemEntity
import com.ibnu.submission2jetpack.data.source.local.room.FavoriteDao
import com.ibnu.submission2jetpack.data.source.local.room.db.DataBase
import com.ibnu.submission2jetpack.utils.EspressoIdlingResource
import com.ibnu.submission2jetpack.utils.TypeUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteRepository(context: Context) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val favoriteDao: FavoriteDao

    init {
        val db = DataBase.getDataBase(context)
        favoriteDao = db.favoriteDao()
    }

    fun getAllFavoriteItems(type: String): DataSource.Factory<Int, FavoriteItemEntity> {
        Timber.d("Getting data..")
        val query = TypeUtils.getSortedByType(type)
        return favoriteDao.getFavoriteItems(query = query)
    }

    fun insertItemIntoFavorite(favoriteItem: FavoriteItemEntity) {
        EspressoIdlingResource.increment()

        executorService.execute {
            EspressoIdlingResource.decrement()
            favoriteDao.insertFavoriteItems(favoriteItem)
        }
    }

    fun checkIsItemFavorite(id: Int): LiveData<Int> {
        val number = MutableLiveData<Int>()
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            favoriteDao.checkItemsIsFavorite(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("Data size is ${it.size}")
                    if (it.size == 1) {
                        number.postValue(it[0].favoriteItemId)
                        Timber.d("Id is is ${it[0].favoriteItemId}")
                    }
                }, {
                    Timber.e(it)
                })
        )
        return number
    }

    fun deleteItemFromFavorite(id: Int) {
        EspressoIdlingResource.increment()

        executorService.execute {
            EspressoIdlingResource.decrement()
            favoriteDao.deleteFavoriteItems(id)
        }
    }

}